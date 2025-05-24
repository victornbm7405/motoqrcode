package br.com.fiap.mottu.motoqrcode.controller;

import br.com.fiap.mottu.motoqrcode.dto.MotoDTO;
import br.com.fiap.mottu.motoqrcode.model.Moto;
import br.com.fiap.mottu.motoqrcode.service.MotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
@CrossOrigin(origins = "*")
public class MotoController {

    private final MotoService service;

    public MotoController(MotoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Moto> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Moto> salvar(@RequestBody MotoDTO dto) {
        Moto moto = service.salvar(dto);
        return ResponseEntity.ok(moto);
    }

    @PostMapping("/qrcode")
    public ResponseEntity<Moto> cadastrarViaQrcode(@RequestBody MotoDTO dto) {
        Moto moto = service.salvarViaQrcode(dto);
        return ResponseEntity.ok(moto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody MotoDTO dto) {
        Moto moto = service.atualizar(id, dto);
        return ResponseEntity.ok(moto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
