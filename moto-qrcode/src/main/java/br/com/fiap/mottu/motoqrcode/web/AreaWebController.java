package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.model.Area;
import br.com.fiap.mottu.motoqrcode.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/areas") // <- mudamos para nao conflitar
public class AreaWebController {

    private final AreaService areaService;

    public AreaWebController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public String listarAreas(Model model) {
        model.addAttribute("areas", areaService.listarTodas());
        return "area/list";
    }

    @GetMapping("/nova")
    public String novaAreaForm(Model model) {
        model.addAttribute("area", new Area());
        return "area/form";
    }

    @PostMapping
    public String salvarArea(@ModelAttribute Area area) {
        areaService.salvar(area);
        return "redirect:/web/areas"; // <- ajustado
    }
}
