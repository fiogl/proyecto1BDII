package com.oracle.demoproyecto1BDII.controller;
import com.oracle.demoproyecto1BDII.model.Producto;
import com.oracle.demoproyecto1BDII.service.ProductoService;
import com.oracle.demoproyecto1BDII.service.CategoriaService;
import com.oracle.demoproyecto1BDII.service.MarcaService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //Elimina un producto por su ID o muestra error si el producto tenía ventas asociadas
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        if (id != null) {
                int resultado = service.eliminarProducto(id); // 0 = eliminado, 1 = no eliminado, -1 = con productos asociados
                if (resultado == 0) {
                    redirectAttrs.addFlashAttribute("mensajeExito", "Producto eliminado correctamente.");
                } else if (resultado == -1) {
                    redirectAttrs.addFlashAttribute("mensajeError", "No se pudo eliminar el producto porque tenía ventas asociadas.");
                }else {
                    redirectAttrs.addFlashAttribute("mensajeError", "El producto que se intentó eliminar no existía.");
                }
        }
        return "redirect:/productos";
    }
}
