package com.oracle.demoproyecto1BDII.controller;
import com.oracle.demoproyecto1BDII.model.Producto;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.CategoriaService;
import com.oracle.demoproyecto1BDII.service.MarcaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService service;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    public ProductoController(ProductoService service, CategoriaService categoriaService, MarcaService marcaService) {
        this.service = service;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", service.listar());
        model.addAttribute("categorias", categoriaService.listar());
        return "productos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("marcas", marcaService.listar());
        return "productos/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Producto producto) {
        service.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto producto = service.buscar(id);
        if (producto == null) {
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("marcas", marcaService.listar());
        return "productos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (id != null) {
            service.eliminar(id);
        }
        return "redirect:/productos";
    }
}
