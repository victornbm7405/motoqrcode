package br.com.fiap.mottu.motoqrcode.service;

import br.com.fiap.mottu.motoqrcode.model.Usuario;
import br.com.fiap.mottu.motoqrcode.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // Debug no console
        System.out.println("👉 Carregado do banco: " + usuario.getUsername() +
                " / senha=" + usuario.getPassword() +
                " / role=" + usuario.getRole());

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRole());

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
