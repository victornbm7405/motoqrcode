package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.model.Moto;
import br.com.fiap.mottu.motoqrcode.dto.MotoDTO;
import br.com.fiap.mottu.motoqrcode.service.MotoService;
import br.com.fiap.mottu.motoqrcode.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/motos") // <- mudamos para nao conflitar
public class MotoWebController {

    private final MotoService motoService;
    private final AreaService areaService;

    public MotoWebController(MotoService motoService, AreaService areaService) {
        this.motoService = motoService;
        this.areaService = areaService;
    }

    @GetMapping
    public String listarMotos(Model model) {
        model.addAttribute("motos", motoService.listarTodas());
        return "moto/list";
    }

    @GetMapping("/nova")
    public String novaMotoForm(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("areas", areaService.listarTodas());
        return "moto/form";
    }

    @PostMapping
    public String salvarMoto(@ModelAttribute Moto moto) {
        MotoDTO dto = new MotoDTO();
        dto.setPlaca(moto.getPlaca());
        dto.setModelo(moto.getModelo());
        dto.setIdArea(moto.getArea().getId());

        motoService.salvar(dto);
        return "redirect:/web/motos"; // <- ajustado
    }
}
