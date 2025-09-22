package com.oracle.demoproyecto1BDII.controller;

import com.oracle.demoproyecto1BDII.DTOs.MarcaVentaDTO;
import com.oracle.demoproyecto1BDII.DTOs.VentaClienteDTO;
import com.oracle.demoproyecto1BDII.service.ConsultaService;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.VentaService;
import com.oracle.demoproyecto1BDII.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

//Controlador encargado de generar los reportes del comercio.
//Proporciona datos sobre productos, ventas, clientes y estadísticas de ventas por marca y cliente.
@Controller
public class ReportesController {
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ConsultaService consultaService;

    // Constructor que inyecta los servicios necesarios
    public ReportesController(ProductoService productoService, VentaService ventaService, ClienteService clienteService, ConsultaService consultaService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.consultaService = consultaService;
    }

    //Genera los datos para la vista de reportes.
    //Calcula totales generales, ventas por marca, ventas por cliente e items comprados.
    //Los agrega al modelo para que puedan mostrarse en la plantilla "reportes".
    @GetMapping("/reportes")
    public String reportes(Model model) {
        // Agregar datos básicos
        model.addAttribute("totalProductos", productoService.listar().size());
        model.addAttribute("totalVentas", ventaService.listar().size());
        model.addAttribute("totalClientes", clienteService.listar().size());

        // Obtiene listas desde el service
        List<MarcaVentaDTO> marcas = consultaService.getMarcasMasVendidas();
        List<VentaClienteDTO> ventasClientes = consultaService.getVentasPorCliente();


        // Calcula la cantidad de elementos vendidos de todas las marcas
        int totalVendidoMarcas = marcas.stream()
                .mapToInt(MarcaVentaDTO::getTotalVendido)
                .sum();
        model.addAttribute("totalVendidoMarcas", totalVendidoMarcas);

        // Calcula el total recaudado por todas las marcas
        BigDecimal totalRecaudadoMarcas = marcas.stream()
                .map(MarcaVentaDTO::getTotalRecaudado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalRecaudadoMarcas", totalRecaudadoMarcas);

        // Calcula la ganancia de la venta de todas las marcas juntas
        BigDecimal totalVentasClientes = ventasClientes.stream()
                .map(VentaClienteDTO::getTotalVendido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalVentasClientes", totalVentasClientes);

        // Calcula la cantidad de veces que el cliente ha realizado compras
        int totalItemsComprados = ventasClientes.stream()
                .mapToInt(VentaClienteDTO::getItemsComprados)
                .sum();
        model.addAttribute("itemsComprados", totalItemsComprados);

        // Envía la lista de los resultados de las consultas
        model.addAttribute("marcas", marcas);
        model.addAttribute("ventasClientes", ventasClientes);
        return "reportes";
    }

}
