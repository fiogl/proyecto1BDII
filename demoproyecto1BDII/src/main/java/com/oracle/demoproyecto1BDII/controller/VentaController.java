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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //Alista las ventas y sus totales, agregándolas al modelo
    @GetMapping
    public String listar(Model model) {
        List<Venta> ventasL = service.listar();

        Map<Long, BigDecimal> totales = new HashMap<>();
        for (Venta v : ventasL) {
            BigDecimal total = detalleVentaService.calcularTotalVenta(v.getId());
            totales.put(v.getId(), total);
        }

        model.addAttribute("ventas", ventasL);
        model.addAttribute("totales", totales);
        model.addAttribute("activePage", "ventas");
        return "ventas";
    }

    // Muestra el formulario de creación de una nueva venta
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        LocalDateTime now = LocalDateTime.now();
        Venta venta = new Venta();
        venta.setFecha(now); // Set current date/time as default

        String maxDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("maxDate", maxDate);

        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("productos", productoService.listar());
        
        // Crea JSON string para JavaScript
        List<Producto> productos = productoService.listar();
        StringBuilder productosJson = new StringBuilder("[");
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (i > 0) productosJson.append(",");
            productosJson.append("{")
                .append("\"id\":").append(p.getId())
                .append(",\"nombre\":\"").append(p.getNombre().replace("\"", "\\\""))
                .append("\",\"precioDetalle\":").append(p.getPrecioDetalle())
                .append("}");
        }
        productosJson.append("]");
        model.addAttribute("productosJson", productosJson.toString());
        
        return "ventas/form";
    }

    //Guarda una venta nueva junto con sus detalles de productos
    @PostMapping
    public String guardar(@ModelAttribute Venta venta, 
                         @RequestParam(value = "productoIds", required = false) List<Long> productoIds,
                         @RequestParam(value = "cantidades", required = false) List<Long> cantidades,
                         @RequestParam(value = "precios", required = false) List<String> precios) {
        
        // Guarda la venta
        Venta ventaGuardada = service.guardar(venta);
        
        // Crea DetalleVenta si se seleccionaron productos
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
                        // Si ocurre un error con algún producto continúa con los demás
                        System.err.println("Error creating DetalleVenta: " + e.getMessage());
                    }
                }
            }
        }
        
        return "redirect:/ventas";
    }

    //Actualiza una venta existente y sus detalles
    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id, 
                           @ModelAttribute Venta venta,
                           @RequestParam(value = "productoIds", required = false) List<Long> productoIds,
                           @RequestParam(value = "cantidades", required = false) List<Long> cantidades,
                           @RequestParam(value = "precios", required = false) List<String> precios,
                           @RequestParam(value = "productosAEliminar", required = false) List<Long> productosAEliminar) {
        
        // Actualiza la venta
        venta.setId(id);
        Venta ventaActualizada = service.guardar(venta);

        // Se encarga de los productos marcados para la eliminación
        if (productosAEliminar != null && !productosAEliminar.isEmpty()) {
            for (Long productoId : productosAEliminar) {
                try {
                    DetalleVentaId detalleId = new DetalleVentaId(id, productoId);
                    detalleVentaService.eliminar(detalleId);
                    System.out.println("Deleted DetalleVenta for product: " + productoId);
                } catch (Exception e) {
                    System.err.println("Error removing DetalleVenta: " + e.getMessage());
                }
            }
        }
        
        // Elmina todos los detalleVentas previos para crear nuevo actualizados
        detalleVentaService.deleteByVentaId(id);
        
        //Crea DetalleVenta si se seleccionaron productos
        if (productoIds != null && cantidades != null && precios != null && 
            productoIds.size() == cantidades.size() && cantidades.size() == precios.size()) {
            
            for (int i = 0; i < productoIds.size(); i++) {
                if (productoIds.get(i) != null && cantidades.get(i) != null && cantidades.get(i) > 0) {
                    try {
                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setId(new DetalleVentaId(ventaActualizada.getId(), productoIds.get(i)));
                        detalle.setVenta(ventaActualizada);
                        detalle.setProducto(productoService.buscar(productoIds.get(i)));
                        detalle.setCantidad(cantidades.get(i));
                        detalle.setPrecioVentaUnidad(new java.math.BigDecimal(precios.get(i)));
                        
                        if (detalleVentaService.validarDetalle(detalle)) {
                            detalleVentaService.guardar(detalle);
                        }
                    } catch (Exception e) {
                        // Si ocurre un error con algún producto continúa con los demás
                        System.err.println("Error creating DetalleVenta: " + e.getMessage());
                    }
                }
            }
        }
        return "redirect:/ventas";
    }

    //Muestra el formulario de edición de una venta existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Venta venta = service.buscar(id);
        if (venta == null) {
            return "redirect:/ventas";
        }

        LocalDateTime now = LocalDateTime.now();
        String maxDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("maxDate", maxDate);

        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("productos", productoService.listar());
        
        // Consigue los detalleVenta de la venta a editar
        List<DetalleVenta> detallesExistentes = detalleVentaService.findByVentaId(id);
        model.addAttribute("detallesExistentes", detallesExistentes);
        
        // Convierte la lista productos en una cadena JSON para JavaScript
        List<Producto> productos = productoService.listar();
        StringBuilder productosJson = new StringBuilder("[");
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (i > 0) productosJson.append(",");
            productosJson.append("{")
                .append("\"id\":").append(p.getId())
                .append(",\"nombre\":\"").append(p.getNombre().replace("\"", "\\\""))
                .append("\",\"precioDetalle\":").append(p.getPrecioDetalle())
                .append("}");
        }
        productosJson.append("]");
        model.addAttribute("productosJson", productosJson.toString());
        
        return "ventas/form";
    }

    // Elimina una venta por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (id != null) {
            service.eliminar(id);
        }
        return "redirect:/ventas";
    }

}

