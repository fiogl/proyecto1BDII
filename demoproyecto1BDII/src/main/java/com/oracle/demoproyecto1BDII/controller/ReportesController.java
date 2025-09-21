package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.model.MarcaVentaDTO;
import com.oracle.demoproyecto1BDII.model.VentaClienteDTO;
import com.oracle.demoproyecto1BDII.service.ConsultaService;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.VentaService;
import com.oracle.demoproyecto1BDII.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReportesController {
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ConsultaService consultaService;

    public ReportesController(ProductoService productoService, VentaService ventaService, ClienteService clienteService, ConsultaService consultaService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.consultaService = consultaService;
    }

    @GetMapping("/reportes")
    public String reportes(Model model) {
        // Agregar datos básicos
        model.addAttribute("totalProductos", productoService.listar().size());
        model.addAttribute("totalVentas", ventaService.listar().size());
        model.addAttribute("totalClientes", clienteService.listar().size());

        // Obtener listas desde el service
        List<MarcaVentaDTO> marcas = consultaService.getMarcasMasVendidas();
        List<VentaClienteDTO> ventasClientes = consultaService.getVentasPorCliente();

        // Calcular total vendido de todas las marcas
        int totalVendidoMarcas = marcas.stream()
                .mapToInt(MarcaVentaDTO::getTotalVendido)
                .sum();

        int totalVentasClientes = consultaService.getVentasPorCliente()
                .stream()
                .mapToInt(VentaClienteDTO::getTotalVendido)
                .sum();

        int totalItemsComprados = ventasClientes.stream()
                .mapToInt(VentaClienteDTO::getItemsComprados)
                .sum();

        model.addAttribute("totalVentasClientes", totalVentasClientes);
        model.addAttribute("marcas", marcas);
        model.addAttribute("totalVendidoMarcas", totalVendidoMarcas);
        model.addAttribute("ventasClientes", ventasClientes);
        model.addAttribute("itemsComprados", totalItemsComprados);

        // Placeholder para estadísticas más complejas
        model.addAttribute("ventasHoy", 0);
        model.addAttribute("ingresosDia", 0);
        model.addAttribute("ingresosMes", 0);

        return "reportes";
    }

}
