package br.com.fiap.mottu.motoqrcode.repository;

import br.com.fiap.mottu.motoqrcode.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { }
