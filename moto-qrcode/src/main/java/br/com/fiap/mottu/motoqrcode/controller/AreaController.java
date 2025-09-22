package br.com.fiap.mottu.motoqrcode.controller;

import br.com.fiap.mottu.motoqrcode.model.Area;
import br.com.fiap.mottu.motoqrcode.service.AreaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaController {

    private final AreaService service;

    public AreaController(AreaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Area> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public Area criar(@RequestBody Area area) {
        return service.salvar(area);
    }

    @PostMapping("/selecionar/{id}")
    public void selecionar(@PathVariable Long id) {
        service.selecionarArea(id);
    }

    @GetMapping("/ativa")
    public Area getAreaAtiva() {
        return service.getAreaAtiva();
    }
}
