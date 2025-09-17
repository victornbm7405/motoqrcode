package br.com.fiap.mottu.motoqrcode.web;
import br.com.fiap.mottu.motoqrcode.model.Moto;
import br.com.fiap.mottu.motoqrcode.repository.AreaRepository;
import br.com.fiap.mottu.motoqrcode.repository.MotoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/motos")
public class MotoWebController {
    private final MotoRepository motoRepository;
    private final AreaRepository areaRepository;
    public MotoWebController(MotoRepository motoRepository, AreaRepository areaRepository) {
        this.motoRepository = motoRepository;
        this.areaRepository = areaRepository;
    }
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("motos", motoRepository.findAll());
        return "moto/list";
    }
    @GetMapping("/nova")
    public String formNova(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("areas", areaRepository.findAll());
        return "moto/form";
    }
    @PostMapping
    public String salvar(@ModelAttribute Moto moto) {
        motoRepository.save(moto);
        return "redirect:/motos";
    }
}
