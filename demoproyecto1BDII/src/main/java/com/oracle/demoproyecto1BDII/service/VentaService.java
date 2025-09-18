package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.Venta;
import com.oracle.demoproyecto1BDII.repo.VentaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentaService {
    private final VentaRepository repo;

    public VentaService (VentaRepository repo) {
        this.repo = repo;
    }

    public List<Venta> listar() { return repo.findAll(); }
    public Venta guardar(Venta v) { return repo.save(v); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public Venta buscar(Long id) { return repo.findById(id).orElse(null); }

}
