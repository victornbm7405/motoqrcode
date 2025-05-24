package br.com.fiap.mottu.motoqrcode.repository;

import br.com.fiap.mottu.motoqrcode.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);
}
