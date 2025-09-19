package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.Categoria;
import com.oracle.demoproyecto1BDII.repo.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repo;

    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public List<Categoria> listar() { return repo.findAll(); }
    public Categoria guardar(Categoria c) { return repo.save(c); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public Categoria buscar(Long id) { return repo.findById(id).orElse(null); }
}
