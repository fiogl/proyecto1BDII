package com.oracle.demoproyecto1BDII.service;
import com.oracle.demoproyecto1BDII.model.DetalleVenta;
import com.oracle.demoproyecto1BDII.model.DetalleVentaId;
import com.oracle.demoproyecto1BDII.repo.DetalleVentaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;



@Service
public class DetalleVentaService {
    private final DetalleVentaRepository repo;


    public DetalleVentaService(DetalleVentaRepository repo, JdbcTemplate jdbcTemplate) {
        this.repo = repo;
    }

    public List<DetalleVenta> listar() { 
        return repo.findAll(); 
    }
    
    public DetalleVenta guardar(DetalleVenta dv) { 
        return repo.save(dv); 
    }
    
    public void eliminar(DetalleVentaId id) { 
        repo.deleteById(id); 
    }
    
    public DetalleVenta buscar(DetalleVentaId id) { 
        return repo.findById(id).orElse(null); 
    }
    
    public List<DetalleVenta> findByVentaId(Long ventaId) {
        return repo.findByVentaId(ventaId);
    }
    
    public void deleteByVentaId(Long ventaId) {
        repo.deleteByVentaId(ventaId);
    }
    
    public List<DetalleVenta> findByProductoId(Long productoId) {
        return repo.findByProductoId(productoId);
    }
    
    // Calcula el subtotal de un detalle de venta.
    // Multiplica la cantidad por el precio unitario,
    // devolviendo cero si alguno de los valores es nulo.
    public BigDecimal calcularSubtotal(DetalleVenta detalle) {
        if (detalle.getCantidad() == null || detalle.getPrecioVentaUnidad() == null) {
            return BigDecimal.ZERO;
        }
        return detalle.getPrecioVentaUnidad().multiply(BigDecimal.valueOf(detalle.getCantidad()));
    }

    //Calcula el total de una venta sumando los subtotales
    //de todos sus detalles asociados.
    public BigDecimal calcularTotalVenta(Long ventaId) {
        List<DetalleVenta> detalles = findByVentaId(ventaId);
        return detalles.stream()
                .map(this::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Valída que un detalle de venta sea correcto.
    // Verifica que cantidad y precio unitario existan y sean mayores que cero,
    // y que estén asociados tanto a una venta como a un producto.
    public boolean validarDetalle(DetalleVenta detalle) {
        return detalle.getCantidad() != null && detalle.getCantidad() > 0 &&
               detalle.getPrecioVentaUnidad() != null && detalle.getPrecioVentaUnidad().compareTo(BigDecimal.ZERO) > 0 &&
               detalle.getVenta() != null && detalle.getProducto() != null;
    }
}
