package com.oracle.demoproyecto1BDII.repo;
import com.oracle.demoproyecto1BDII.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
