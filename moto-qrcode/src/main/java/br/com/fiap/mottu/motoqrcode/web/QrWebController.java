package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.dto.MotoDTO;
import br.com.fiap.mottu.motoqrcode.model.Moto;
import br.com.fiap.mottu.motoqrcode.service.AreaService;
import br.com.fiap.mottu.motoqrcode.service.MotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/qrcode")
public class QrWebController {

    private final MotoService motoService;
    private final AreaService areaService;

    public QrWebController(MotoService motoService, AreaService areaService) {
        this.motoService = motoService;
        this.areaService = areaService;
    }

    @GetMapping
    public String scanPage(Model model) {
        // opcional: expõe badge da ativa
        try { model.addAttribute("areaAtiva", areaService.getAreaAtiva()); } catch (RuntimeException ignored) {}
        return "qrcode/scan";
    }

    @PostMapping("/submit")
    @ResponseBody
    public ResponseEntity<?> submit(@RequestBody MotoDTO dto) {
        // Se o QR não mandou a área, usa a ativa do service
        if (dto.getIdArea() == null) {
            try {
                dto.setIdArea(areaService.getAreaAtiva().getId());
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body("Nenhuma área ativa definida.");
            }
        }

        Moto salvo = motoService.salvarViaQrcode(dto);
        return ResponseEntity.ok(salvo);
    }
}
