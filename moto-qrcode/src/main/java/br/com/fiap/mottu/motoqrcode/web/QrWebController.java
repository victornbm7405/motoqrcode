package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.dto.MotoDTO;
import br.com.fiap.mottu.motoqrcode.model.Moto;
import br.com.fiap.mottu.motoqrcode.service.MotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/qrcode")
public class QrWebController {

    private final MotoService motoService;

    public QrWebController(MotoService motoService) {
        this.motoService = motoService;
    }

    // Página com a câmera para ler o QR
    @GetMapping
    public String scanPage(Model model) {
        return "qrcode/scan"; // templates/qrcode/scan.html
    }

    // Recebe o JSON decodificado do QR (placa, modelo)
    @PostMapping("/submit")
    @ResponseBody
    public ResponseEntity<Moto> submit(@RequestBody MotoDTO dto) {
        // Reaproveita a sua regra do QR (usa Área Ativa do AreaService)
        Moto salvo = motoService.salvarViaQrcode(dto);
        return ResponseEntity.ok(salvo);
    }
}
