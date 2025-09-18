package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.Provincia;
import com.oracle.demoproyecto1BDII.repo.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {
    private final ProvinciaRepository repo;

    public ProvinciaService(ProvinciaRepository repo) {
        this.repo = repo;
    }

    public List<Provincia> listar() { return repo.findAll(); }
    public Provincia guardar(Provincia p) { return repo.save(p); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public Provincia buscar(Long id) { return repo.findById(id).orElse(null); }
}
