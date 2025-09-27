package br.com.fiap.mottu.motoqrcode.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthRepository repo;

    public CustomUserDetailsService(UserAuthRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        String role = u.getRole();
        if (role == null) {
            throw new UsernameNotFoundException("Usuário sem ROLE configurada: " + username);
        }
        role = role.trim().toUpperCase();

        if (!role.equals("ADMIN") && !role.equals("USER")) {
            throw new UsernameNotFoundException("ROLE inválida para usuário " + username + ": " + role);
        }

        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword()) // mantém seu encoder atual
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + role)))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
