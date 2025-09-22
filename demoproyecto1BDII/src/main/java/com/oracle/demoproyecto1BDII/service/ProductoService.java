package com.oracle.demoproyecto1BDII.service;
import com.oracle.demoproyecto1BDII.model.Producto;
import com.oracle.demoproyecto1BDII.repo.ProductoRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall eliminarProductoCall;

    public ProductoService(ProductoRepository repo, JdbcTemplate jdbcTemplate) {
        this.repo = repo;
        this.jdbcTemplate = jdbcTemplate;
        this.eliminarProductoCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PK_OPR_MODIFICACION") // nombre del paquete
                .withFunctionName("ELIMINAR_PRODUCTO"); // ahora es función, no procedimiento
    }

    public List<Producto> listar() { return repo.findAll(); }
    public Producto guardar(Producto p) { return repo.save(p); }
    public Producto buscar(Long id) { return repo.findById(id).orElse(null); }

    // Ejecuta la función SQL del paquete de operaciones para eliminar el producto
    public int eliminarProducto(Long productoId) {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_PRODUCTO_ID", productoId);

        try {
            // Ejecuta la función y recibe un BigDecimal
            Number resultado = eliminarProductoCall.executeFunction(Number.class, inParams);
            return resultado.intValue(); // lo convertimos a int para manejarlo fácilmente
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al eliminar el producto: " + e.getMessage(), e);
        }
    }
}
