package br.com.fiap.mottu.motoqrcode.web;
import br.com.fiap.mottu.motoqrcode.model.Area;
import br.com.fiap.mottu.motoqrcode.repository.AreaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/areas")
public class AreaWebController {
    private final AreaRepository areaRepository;
    public AreaWebController(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("areas", areaRepository.findAll());
        return "area/list";
    }
    @GetMapping("/nova")
    public String formNova(Model model) {
        model.addAttribute("area", new Area());
        return "area/form";
    }
    @PostMapping
    public String salvar(@ModelAttribute Area area) {
        areaRepository.save(area);
        return "redirect:/areas";
    }
}
