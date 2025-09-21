package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.VentaService;
import com.oracle.demoproyecto1BDII.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportesController {
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final ClienteService clienteService;

    public ReportesController(ProductoService productoService, VentaService ventaService, ClienteService clienteService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
        this.clienteService = clienteService;
    }

    @GetMapping("/reportes")
    public String reportes(Model model) {
        // Agregar datos básicos para los reportes
        model.addAttribute("totalProductos", productoService.listar().size());
        model.addAttribute("totalVentas", ventaService.listar().size());
        model.addAttribute("totalClientes", clienteService.listar().size());
        
        // Placeholder para estadísticas más complejas
        model.addAttribute("ventasHoy", 0);
        model.addAttribute("ingresosDia", 0);
        model.addAttribute("ingresosMes", 0);
        
        return "reportes";
    }
}
