package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.model.Usuario;
import br.com.fiap.mottu.motoqrcode.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios-page")
public class UsuarioWebController {

    private final UsuarioService service;

    public UsuarioWebController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = service.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/list"; // templates/usuarios/list.html
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarioId", null);
        return "usuarios/form"; // templates/usuarios/form.html
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Usuario usuario, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("usuarioId", null);
            return "usuarios/form";
        }
        service.salvar(usuario);
        return "redirect:/usuarios-page";
    }

    // --------- NOVOS ENDPOINTS ---------

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarioId", id); // usado no form pra decidir PUT/POST
        return "usuarios/form";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute Usuario usuario,
                            BindingResult br,
                            Model model) {
        if (br.hasErrors()) {
            model.addAttribute("usuarioId", id);
            return "usuarios/form";
        }
        service.atualizar(id, usuario);
        return "redirect:/usuarios-page";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/usuarios-page";
    }
}
