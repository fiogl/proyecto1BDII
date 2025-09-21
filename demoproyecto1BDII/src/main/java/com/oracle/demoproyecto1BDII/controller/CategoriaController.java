package com.oracle.demoproyecto1BDII.controller;
import com.oracle.demoproyecto1BDII.model.Categoria;
import com.oracle.demoproyecto1BDII.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", service.listar());
        return "categoria/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Categoria categoria) {
        service.guardar(categoria);
        return "redirect:/categoria";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Categoria categoria = service.buscar(id);
        if (categoria == null) {
            return "redirect:/categoria";
        }
        model.addAttribute("categoria", categoria);
        return "categoria/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (id != null) {
            service.eliminar(id);
        }
        return "redirect:/categoria";
    }
}
