package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.dto.MotoDTO;
import br.com.fiap.mottu.motoqrcode.model.Moto;
import br.com.fiap.mottu.motoqrcode.service.MotoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/motos-page")
public class MotoWebController {

    private final MotoService service;

    public MotoWebController(MotoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        List<Moto> motos = service.listarTodas();
        model.addAttribute("motos", motos);
        return "moto/list"; // templates/moto/list.html
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("moto", new MotoDTO());
        return "moto/form"; // templates/moto/form.html
    }

    @PostMapping
    public String salvar(@ModelAttribute MotoDTO dto) {
        service.salvar(dto);
        return "redirect:/motos-page";
    }
}
