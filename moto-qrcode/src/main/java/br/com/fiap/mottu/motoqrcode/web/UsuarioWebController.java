package br.com.fiap.mottu.motoqrcode.web;

import br.com.fiap.mottu.motoqrcode.model.Usuario;
import br.com.fiap.mottu.motoqrcode.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    private final UsuarioRepository repo;
    public UsuarioWebController(UsuarioRepository repo) { this.repo = repo; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", repo.findAll());
        return "usuarios/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Novo Usuário");
        return "usuarios/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("usuario") Usuario usuario,
                        BindingResult br,
                        RedirectAttributes ra) {
        if (br.hasErrors()) return "usuarios/form";
        repo.save(usuario);
        ra.addFlashAttribute("msg", "Usuário criado com sucesso!");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("usuario", u);
        model.addAttribute("titulo", "Editar Usuário");
        return "usuarios/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult br,
                            RedirectAttributes ra) {
        if (br.hasErrors()) return "usuarios/form";
        usuario.setId(id);
        repo.save(usuario);
        ra.addFlashAttribute("msg", "Usuário atualizado com sucesso!");
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("msg", "Usuário excluído!");
        return "redirect:/usuarios";
    }
}
