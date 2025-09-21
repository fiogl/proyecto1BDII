package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.model.DetalleVenta;
import com.oracle.demoproyecto1BDII.model.DetalleVentaId;
import com.oracle.demoproyecto1BDII.service.DetalleVentaService;
import com.oracle.demoproyecto1BDII.service.VentaService;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/detalleVenta")
public class DetalleVentaController {
    private final DetalleVentaService service;
    private final VentaService ventaService;
    private final ProductoService productoService;

    public DetalleVentaController(DetalleVentaService service, VentaService ventaService, ProductoService productoService) {
        this.service = service;
        this.ventaService = ventaService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalleVentas", service.listar());
        return "detalleVenta/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setId(new DetalleVentaId());
        model.addAttribute("detalleVenta", detalleVenta);
        model.addAttribute("ventas", ventaService.listar());
        model.addAttribute("productos", productoService.listar());
        return "detalleVenta/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute DetalleVenta detalleVenta) {
        if (service.validarDetalle(detalleVenta)) {
            service.guardar(detalleVenta);
        }
        return "redirect:/detalleVenta";
    }

    @GetMapping("/editar/{ventaId}/{productoId}")
    public String editar(@PathVariable Long ventaId, @PathVariable Long productoId, Model model) {
        DetalleVentaId id = new DetalleVentaId(ventaId, productoId);
        DetalleVenta detalleVenta = service.buscar(id);
        if (detalleVenta == null) {
            return "redirect:/detalleVenta";
        }
        model.addAttribute("detalleVenta", detalleVenta);
        model.addAttribute("ventas", ventaService.listar());
        model.addAttribute("productos", productoService.listar());
        return "detalleVenta/form";
    }

    @GetMapping("/eliminar/{ventaId}/{productoId}")
    public String eliminar(@PathVariable Long ventaId, @PathVariable Long productoId) {
        DetalleVentaId id = new DetalleVentaId(ventaId, productoId);
        service.eliminar(id);
        return "redirect:/detalleVenta";
    }

    @GetMapping("/venta/{ventaId}")
    public String findByVenta(@PathVariable Long ventaId, Model model) {
        model.addAttribute("detalleVentas", service.findByVentaId(ventaId));
        model.addAttribute("ventaId", ventaId);
        return "detalleVenta/listar";
    }
}
