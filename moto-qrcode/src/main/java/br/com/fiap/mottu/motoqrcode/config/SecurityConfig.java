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

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // para ambiente acadêmico; em produção use BCryptPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    DaoAuthenticationProvider authProvider(PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // estáticos e login públicos
                        .requestMatchers("/css/**","/js/**","/images/**","/login","/error").permitAll()

                        // >>> exceções para o fluxo do QR (scanner desktop e REST existente)
                        .requestMatchers(HttpMethod.POST, "/api/motos/qrcode").permitAll()
                        .requestMatchers(HttpMethod.POST, "/motos/qrcode").permitAll()

                        // páginas Thymeleaf (GET) — USER e ADMIN
                        .requestMatchers(HttpMethod.GET, "/", "/areas-page/**", "/motos-page/**", "/usuarios-page/**").hasAnyRole("ADMIN","USER")

                        // APIs de leitura — USER e ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN","USER")

                        // escritas em geral — só ADMIN
                        .requestMatchers(HttpMethod.POST,   "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

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
