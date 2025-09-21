package com.oracle.demoproyecto1BDII.controller;
import com.oracle.demoproyecto1BDII.model.Venta;
import com.oracle.demoproyecto1BDII.model.DetalleVenta;
import com.oracle.demoproyecto1BDII.model.DetalleVentaId;
import com.oracle.demoproyecto1BDII.model.Producto;
import com.oracle.demoproyecto1BDII.service.VentaService;
import com.oracle.demoproyecto1BDII.service.ClienteService;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.DetalleVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {
    private final VentaService service;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final DetalleVentaService detalleVentaService;

    public VentaController(VentaService service, ClienteService clienteService, ProductoService productoService, DetalleVentaService detalleVentaService) {
        this.service = service;
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", service.listar());
        model.addAttribute("activePage", "ventas");
        return "ventas";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now()); // Set current date/time as default
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("productos", productoService.listar());
        return "ventas/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Venta venta, 
                         @RequestParam(value = "productoIds", required = false) List<Long> productoIds,
                         @RequestParam(value = "cantidades", required = false) List<Long> cantidades,
                         @RequestParam(value = "precios", required = false) List<String> precios) {
        
        // Save the venta first
        Venta ventaGuardada = service.guardar(venta);
        
        // Create DetalleVenta records if products are provided
        if (productoIds != null && cantidades != null && precios != null && 
            productoIds.size() == cantidades.size() && cantidades.size() == precios.size()) {
            
            for (int i = 0; i < productoIds.size(); i++) {
                if (productoIds.get(i) != null && cantidades.get(i) != null && cantidades.get(i) > 0) {
                    try {
                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setId(new DetalleVentaId(ventaGuardada.getId(), productoIds.get(i)));
                        detalle.setVenta(ventaGuardada);
                        detalle.setProducto(productoService.buscar(productoIds.get(i)));
                        detalle.setCantidad(cantidades.get(i));
                        detalle.setPrecioVentaUnidad(new java.math.BigDecimal(precios.get(i)));
                        
                        if (detalleVentaService.validarDetalle(detalle)) {
                            detalleVentaService.guardar(detalle);
                        }
                    } catch (Exception e) {
                        // Log error and continue with other products
                        System.err.println("Error creating DetalleVenta: " + e.getMessage());
                    }
                }
            }
        }
        
        return "redirect:/ventas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Venta venta = service.buscar(id);
        if (venta == null) {
            return "redirect:/ventas";
        }
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listar());
        return "ventas/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (id != null) {
            service.eliminar(id);
        }
        return "redirect:/ventas";
    }
}

