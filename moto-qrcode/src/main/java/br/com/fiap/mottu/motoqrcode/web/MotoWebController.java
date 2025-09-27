package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.dto.MotoDTO;
import br.com.fiap.mottu.motoqrcode.service.AreaService;
import br.com.fiap.mottu.motoqrcode.service.MotoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motos-page")
public class MotoWebController {

    private final MotoService service;
    private final AreaService areaService;

    public MotoWebController(MotoService service, AreaService areaService) {
        this.service = service;
        this.areaService = areaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("motos", service.listarTodas());
        return "moto/list"; // templates/moto/list.html
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("moto", new MotoDTO());
        model.addAttribute("areas", areaService.listarTodas()); // para <select>
        return "moto/form"; // templates/moto/form.html
    }

    @PostMapping
    public String salvar(@ModelAttribute MotoDTO dto) {
        service.salvar(dto);
        return "redirect:/motos-page";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var moto = service.buscarPorId(id); // usa o método adicionado acima

        MotoDTO dto = new MotoDTO();
        dto.setPlaca(moto.getPlaca());
        dto.setModelo(moto.getModelo());
        dto.setIdArea(moto.getArea() != null ? moto.getArea().getId() : null);

        model.addAttribute("moto", dto);
        model.addAttribute("areas", areaService.listarTodas());
        model.addAttribute("motoId", id); // usamos isso na action do form
        return "moto/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute MotoDTO dto) {
        service.atualizar(id, dto); // já existe no seu service
        return "redirect:/motos-page";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.deletar(id); // já existe no seu service
        return "redirect:/motos-page";
    }

}
