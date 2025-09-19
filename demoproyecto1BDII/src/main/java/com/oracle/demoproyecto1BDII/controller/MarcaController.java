package com.oracle.demoproyecto1BDII.controller;
import com.oracle.demoproyecto1BDII.model.Marca;
import com.oracle.demoproyecto1BDII.service.MarcaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marca")
public class MarcaController {
    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("marca", service.listar());
        return "marca/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("marca", new Marca());
        return "marca/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Marca marca) {
        service.guardar(marca);
        return "redirect:/cliente";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("marca", service.buscar(id));
        return "marca/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/marca";
    }
}
