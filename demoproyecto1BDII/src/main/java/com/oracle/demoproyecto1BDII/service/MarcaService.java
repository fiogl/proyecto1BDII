package com.oracle.demoproyecto1BDII.service;
import com.oracle.demoproyecto1BDII.model.Marca;
import com.oracle.demoproyecto1BDII.repo.MarcaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarcaService {
    private final MarcaRepository repo;

    public MarcaService(MarcaRepository repo) {
        this.repo = repo;
    }

    public List<Marca> listar() { return repo.findAll(); }
    public Marca guardar(Marca m) { return repo.save(m); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public Marca buscar(Long id) { return repo.findById(id).orElse(null); }
}
