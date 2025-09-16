package br.com.fiap.mottu.motoqrcode.service;

import br.com.fiap.mottu.motoqrcode.dto.MotoDTO;
import br.com.fiap.mottu.motoqrcode.model.Area;
import br.com.fiap.mottu.motoqrcode.model.Moto;
import br.com.fiap.mottu.motoqrcode.repository.AreaRepository;
import br.com.fiap.mottu.motoqrcode.repository.MotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    private final MotoRepository motoRepository;
    private final AreaRepository areaRepository;
    private final AreaService areaService;

    public MotoService(MotoRepository motoRepository, AreaRepository areaRepository, AreaService areaService) {
        this.motoRepository = motoRepository;
        this.areaRepository = areaRepository;
        this.areaService = areaService;
    }

    public Moto salvar(MotoDTO dto) {
        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());

        Area area = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new RuntimeException("Ãrea nÃ£o encontrada"));

        moto.setArea(area);

        return motoRepository.save(moto);
    }

    public Moto salvarViaQrcode(MotoDTO dto) {
        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());

        Area area = areaService.getAreaAtiva();
        moto.setArea(area);

        return motoRepository.save(moto);
    }

    public Moto atualizar(Long id, MotoDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto nÃ£o encontrada"));

        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());

        Area area = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new RuntimeException("Ãrea nÃ£o encontrada"));

        moto.setArea(area);

        return motoRepository.save(moto);
    }

    public void deletar(Long id) {
        motoRepository.deleteById(id);
    }

    public List<Moto> listarTodas() {
        return motoRepository.findAll();
    }
}
