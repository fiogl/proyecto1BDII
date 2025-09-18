package com.oracle.demoproyecto1BDII.service;
import com.oracle.demoproyecto1BDII.model.Cliente;
import com.oracle.demoproyecto1BDII.repo.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listar() { return repo.findAll(); }
    public Cliente guardar(Cliente c) { return repo.save(c); }
    public void eliminar(Long id) { repo.deleteById(id); }
    public Cliente buscar(Long id) { return repo.findById(id).orElse(null); }
}
