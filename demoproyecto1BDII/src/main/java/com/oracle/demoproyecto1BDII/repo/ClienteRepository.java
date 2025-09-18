package com.oracle.demoproyecto1BDII.repo;
import com.oracle.demoproyecto1BDII.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
