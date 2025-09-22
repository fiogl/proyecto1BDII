package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.Venta;
import com.oracle.demoproyecto1BDII.repo.VentaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {
    private final VentaRepository repo;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall eliminarVentaCall;

    public VentaService(VentaRepository repo, JdbcTemplate jdbcTemplate) {
        this.repo = repo;
        this.jdbcTemplate = jdbcTemplate;
        this.eliminarVentaCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PK_OPR_MODIFICACION") // nombre del paquete
                .withProcedureName("ELIMINAR_VENTA");
    }

    public List<Venta> listar() { return repo.findAll(); }

    public Venta guardar(Venta v) { return repo.save(v); }

    public Venta buscar(Long id) { return repo.findById(id).orElse(null); }

    // Ejecuta el procedimiento SQL del paquete de operaciones para eliminar la venta
    public void eliminarVenta(Long ventaId) {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_VENTA_ID", ventaId);
        try {
            eliminarVentaCall.execute(inParams); // Ejecuta el procedimiento con los parámetros
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al eliminar la venta: " + e.getMessage(), e);
        }
    }

}
