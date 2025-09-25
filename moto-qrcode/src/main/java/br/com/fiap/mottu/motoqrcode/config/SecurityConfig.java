package br.com.fiap.mottu.motoqrcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // DEV/APRENDIZADO
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService uds;

    public SecurityConfig(UserDetailsService uds) {
        this.uds = uds;
    }

    @Bean @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        // ATENÇÃO: senha em texto puro só para ambiente acadêmico/dev.
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authProvider())
                .authorizeHttpRequests(auth -> auth
                        // públicos
                        .requestMatchers("/css/**","/js/**","/images/**","/login","/error").permitAll()

                        // LEITURA (GET): ADMIN e USER
                        .requestMatchers(HttpMethod.GET,
                                "/",
                                "/areas/**",
                                "/motos/**",
                                "/usuarios/**",
                                "/api/**"
                        ).hasAnyRole("ADMIN","USER")

                        // ESCRITA (POST/PUT/PATCH/DELETE): apenas ADMIN
                        .requestMatchers(HttpMethod.POST,   "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                        // fallback
                        .anyRequest().authenticated()
                )
                .formLogin(fl -> fl
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
                .csrf(Customizer.withDefaults()); // CSRF ON p/ forms Thymeleaf

        return http.build();
    }
}
