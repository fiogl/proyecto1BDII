package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ProductoService productoService;

    public HomeController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // Agrega estadísticas básicas al modelo
        model.addAttribute("totalProductos", productoService.listar().size());
        return "home";
    }
}