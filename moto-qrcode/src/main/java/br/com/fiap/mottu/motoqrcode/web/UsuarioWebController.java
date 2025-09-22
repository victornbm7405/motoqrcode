package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.model.Usuario;
import br.com.fiap.mottu.motoqrcode.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "usuarios/form"; // templates/usuarios/form.html
    }

    @PostMapping
    public String salvar(@ModelAttribute Usuario usuario) {
        service.salvar(usuario);
        return "redirect:/usuarios-page";
    }
}
