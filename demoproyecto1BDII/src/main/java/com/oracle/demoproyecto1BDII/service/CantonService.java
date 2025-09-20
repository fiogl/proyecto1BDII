package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.Canton;
import com.oracle.demoproyecto1BDII.repo.CantonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CantonService {
    private final CantonRepository repo;

    public CantonService(CantonRepository repo) {
        this.repo = repo;
    }

    public List<Canton> listar() { return repo.findAll(); }
    public Canton guardar(Canton c) { return repo.save(c); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public Canton buscar(Long id) { return repo.findById(id).orElse(null); }
    public List<Canton> listarPorProvincia(Long idProvincia) { return repo.findByProvinciaId(idProvincia); }
}
