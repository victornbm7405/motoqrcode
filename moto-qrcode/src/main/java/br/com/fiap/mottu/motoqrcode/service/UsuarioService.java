package br.com.fiap.mottu.motoqrcode.service;

import br.com.fiap.mottu.motoqrcode.model.Usuario;
import br.com.fiap.mottu.motoqrcode.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }

    public Usuario salvar(Usuario usuario) {
        // Se quiser garantir role upper-case:
        if (usuario.getRole() != null) {
            usuario.setRole(usuario.getRole().toUpperCase());
        }
        return repository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        return repository.findById(id)
                .map(existente -> {
                    existente.setUsername(usuario.getUsername());
                    existente.setEmail(usuario.getEmail());
                    existente.setNome(usuario.getNome());

                    // Atualiza role (em upper-case por conferência)
                    if (usuario.getRole() != null) {
                        existente.setRole(usuario.getRole().toUpperCase());
                    }

                    // Se a senha vier vazia/nula, manter a atual
                    if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
                        existente.setPassword(usuario.getPassword());
                    }

                    return repository.save(existente);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
