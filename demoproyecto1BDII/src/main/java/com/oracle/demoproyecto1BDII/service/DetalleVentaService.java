package com.oracle.demoproyecto1BDII.service;
import com.oracle.demoproyecto1BDII.model.Cliente;
import com.oracle.demoproyecto1BDII.model.DetalleVenta;
import com.oracle.demoproyecto1BDII.repo.ClienteRepository;
import com.oracle.demoproyecto1BDII.repo.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetalleVentaService {
    private final DetalleVentaRepository repo;

    public DetalleVentaService(DetalleVentaRepository repo) {
        this.repo = repo;
    }

    public List<DetalleVenta> listar() { return repo.findAll(); }
    public DetalleVenta guardar(DetalleVenta dv) { return repo.save(dv); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public DetalleVenta buscar(Long id) { return repo.findById(id).orElse(null); }
}
