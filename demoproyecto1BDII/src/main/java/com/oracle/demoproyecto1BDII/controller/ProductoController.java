package com.oracle.demoproyecto1BDII.controller;
import com.oracle.demoproyecto1BDII.model.Producto;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.CategoriaService;
import com.oracle.demoproyecto1BDII.service.MarcaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Controlador para gestionar productos.
//Permite listar, crear, editar y eliminar productos,
//y carga datos relacionados como categorías y marcas.
@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService service;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    //Constructor que inyecta los servicios necesarios
    public ProductoController(ProductoService service, CategoriaService categoriaService, MarcaService marcaService) {
        this.service = service;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }

    //Lista todos los productos y sus categorías asociadas.
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", service.listar());
        model.addAttribute("categorias", categoriaService.listar());
        return "productos";
    }

    //Muestra el formulario para crear un nuevo producto,
    //cargando categorías y marcas disponibles.
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("marcas", marcaService.listar());
        return "productos/form";
    }

    //Guarda un producto nuevo o editado.
    @PostMapping
    public String guardar(@ModelAttribute Producto producto) {
        service.guardar(producto);
        return "redirect:/productos";
    }

    //Muestra el formulario para editar un producto existente.
    //Si no se encuentra, redirige al listado.
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

    //Elimina un producto por su ID si existe.
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (id != null) {
            service.eliminar(id);
        }
        return "redirect:/productos";
    }
}
