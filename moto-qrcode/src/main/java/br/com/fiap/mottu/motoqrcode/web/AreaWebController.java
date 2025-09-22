package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.model.Area;
import br.com.fiap.mottu.motoqrcode.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/areas-page")
public class AreaWebController {

    private final AreaService service;

    public AreaWebController(AreaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        List<Area> areas = service.listarTodas();
        model.addAttribute("areas", areas);
        return "area/list"; // templates/area/list.html
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("area", new Area());
        return "area/form"; // templates/area/form.html
    }

    @PostMapping
    public String salvar(@ModelAttribute Area area) {
        service.salvar(area);
        return "redirect:/areas-page";
    }
}
