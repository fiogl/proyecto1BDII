package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.model.Canton;
import com.oracle.demoproyecto1BDII.model.Cliente;
import com.oracle.demoproyecto1BDII.model.Provincia;
import com.oracle.demoproyecto1BDII.service.CantonService;
import com.oracle.demoproyecto1BDII.service.ClienteService;
import com.oracle.demoproyecto1BDII.service.ProvinciaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService service;
    private final ProvinciaService provinciaService;
    private final CantonService cantonService;

    public ClienteController(ClienteService service, ProvinciaService provinciaService, CantonService cantonService) {
        this.service = service;
        this.provinciaService = provinciaService;
        this.cantonService = cantonService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("provincias", provinciaService.listar());

        // Crear e inicializar completamente el objeto cliente para el modal
        Cliente clienteVacio = new Cliente();
        Canton canton = new Canton();
        Provincia provincia = new Provincia();

        // Inicializar la cadena completa de objetos para th:field anidados
        canton.setProvincia(provincia);
        clienteVacio.setCanton(canton);

        model.addAttribute("cliente", clienteVacio);
        model.addAttribute("provincias", provinciaService.listar());
        return "cliente/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("provincias", provinciaService.listar());
        return "cliente/form";
    }

    @GetMapping("/cantones")
    @ResponseBody
    public List<Canton> obtenerCantones(@RequestParam Long id_provincia) {
        return cantonService.listarPorProvincia(id_provincia);
    }

    @PostMapping
    public String guardar(@ModelAttribute Cliente cliente) {
        if (cliente.getCanton() != null && cliente.getCanton().getIdCanton() != null) {
            Canton canton = cantonService.buscar(cliente.getCanton().getIdCanton());
            cliente.setCanton(canton);
        }

        service.guardar(cliente);
        return "redirect:/cliente";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscar(id));
        return "cliente/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/cliente";
    }
}
