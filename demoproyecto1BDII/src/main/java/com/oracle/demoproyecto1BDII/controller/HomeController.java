package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ProductoService productoService;
    private final VentaService ventaService;

    public HomeController(ProductoService productoService, VentaService ventaService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // Agregar estadísticas básicas al modelo
        model.addAttribute("totalProductos", productoService.listar().size());
        model.addAttribute("ventasHoy", 0); // Placeholder - implementar lógica de ventas del día
        model.addAttribute("ingresos", 0); // Placeholder - implementar lógica de ingresos del mes
        return "home";
    }
}