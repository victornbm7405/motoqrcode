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

    // Listar todos
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Buscar por username
    public Optional<Usuario> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }

    // Salvar
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    // Atualizar
    public Usuario atualizar(Long id, Usuario usuario) {
        return repository.findById(id)
                .map(existente -> {
                    existente.setUsername(usuario.getUsername());
                    existente.setPassword(usuario.getPassword());
                    existente.setRole(usuario.getRole());
                    return repository.save(existente);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    // Deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
