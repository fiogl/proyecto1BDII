package com.oracle.demoproyecto1BDII.repo;
import com.oracle.demoproyecto1BDII.model.DetalleVenta;
import com.oracle.demoproyecto1BDII.model.DetalleVentaId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, DetalleVentaId> {
    List<DetalleVenta> findByVentaId(Long ventaId);
    void deleteByVentaId(Long ventaId);
    List<DetalleVenta> findByProductoId(Long productoId);
}
