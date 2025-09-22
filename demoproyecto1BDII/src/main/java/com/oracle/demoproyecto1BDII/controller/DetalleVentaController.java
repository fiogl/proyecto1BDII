package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.model.DetalleVenta;
import com.oracle.demoproyecto1BDII.model.DetalleVentaId;
import com.oracle.demoproyecto1BDII.service.DetalleVentaService;
import com.oracle.demoproyecto1BDII.service.VentaService;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Controlador para gestionar los detalles de venta.
//Permite listar, crear, editar, eliminar y consultar detalles de venta
//asociados a productos y ventas.

@Controller
@RequestMapping("/detalleVenta")
public class DetalleVentaController {
    private final DetalleVentaService service;
    private final VentaService ventaService;
    private final ProductoService productoService;

    //Constructor que inyecta los servicios necesarios
    public DetalleVentaController(DetalleVentaService service, VentaService ventaService, ProductoService productoService) {
        this.service = service;
        this.ventaService = ventaService;
        this.productoService = productoService;
    }

    //Lista todos los detalles de venta.
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalleVentas", service.listar());
        return "detalleVenta/listar";
    }

    //Muestra el formulario para crear un nuevo detalle de venta.
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setId(new DetalleVentaId());
        model.addAttribute("detalleVenta", detalleVenta);
        model.addAttribute("ventas", ventaService.listar());
        model.addAttribute("productos", productoService.listar());
        return "detalleVenta/form";
    }

    //Guarda un detalle de venta nuevo o editado si es válido.
    @PostMapping
    public String guardar(@ModelAttribute DetalleVenta detalleVenta) {
        if (service.validarDetalle(detalleVenta)) {
            service.guardar(detalleVenta);
        }
        return "redirect:/detalleVenta";
    }

    //Muestra el formulario para editar un detalle de venta existente.
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


    //Lista todos los detalles de venta de una venta específica.
    @GetMapping("/venta/{ventaId}")
    public String findByVenta(@PathVariable Long ventaId, Model model) {
        model.addAttribute("detalleVentas", service.findByVentaId(ventaId));
        model.addAttribute("ventaId", ventaId);
        return "detalleVenta/listar";
    }
}
