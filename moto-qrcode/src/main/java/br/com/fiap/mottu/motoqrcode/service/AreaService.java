package br.com.fiap.mottu.motoqrcode.service;

import br.com.fiap.mottu.motoqrcode.model.Area;
import br.com.fiap.mottu.motoqrcode.repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private final AreaRepository repository;
    private Area areaAtiva;

    public AreaService(AreaRepository repository) {
        this.repository = repository;
    }

    public Area buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ãrea nÃ£o encontrada"));
    }

    public Area salvar(Area area) {
        return repository.save(area);
    }

    public void selecionarArea(Long id) {
        this.areaAtiva = buscarPorId(id);
        System.out.println("Ãrea ativa selecionada: " + areaAtiva.getNome());
    }

    public Area getAreaAtiva() {
        if (areaAtiva == null) {
            throw new RuntimeException("Nenhuma Ã¡rea foi selecionada ainda");
        }
        return areaAtiva;
    }

    public List<Area> listarTodas() {
        List<Area> todas = repository.findAll();
        System.out.println("Ãreas disponÃ­veis no banco:");
        todas.forEach(a -> System.out.println("- ID: " + a.getId() + " | Nome: " + a.getNome()));
        return todas;
    }
}
