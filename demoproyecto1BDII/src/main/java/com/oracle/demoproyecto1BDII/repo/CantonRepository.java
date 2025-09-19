package com.oracle.demoproyecto1BDII.repo;
import com.oracle.demoproyecto1BDII.model.Canton;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CantonRepository extends JpaRepository<Canton, Long> {
    List<Canton> findByProvinciaIdProvincia(Long idProvincia);
}
