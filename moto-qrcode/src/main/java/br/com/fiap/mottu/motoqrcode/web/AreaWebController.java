package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.model.Area;
import br.com.fiap.mottu.motoqrcode.service.AreaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/areas-page")
public class AreaWebController {

    private final AreaService service;

    public AreaWebController(AreaService service) {
        this.service = service;
    }

    @ModelAttribute("requestPath")
    String requestPath(HttpServletRequest req) {
        return req.getRequestURI();
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("areas", service.listarTodas());
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

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("area", service.buscarPorId(id));
        return "area/form"; // reutiliza o mesmo form
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Area form) {
        service.atualizar(id, form); // adicionamos este método no service
        return "redirect:/areas-page";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.deletar(id); // adicionamos este método no service
        return "redirect:/areas-page";
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // NOVO ENDPOINT: definir Área Ativa (sem alterar nada do restante)
    // POST /areas-page/ativa  (parâmetro: areaId)
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @PostMapping("/ativa")
    public String definirAtiva(@RequestParam("areaId") Long areaId,
                               org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        service.selecionarArea(areaId); // grava a ativa em memória
        ra.addFlashAttribute("msgSucesso", "Área ativa definida: " + service.getAreaAtiva().getNome());
        return "redirect:/areas-page";
    }



}
