package com.oracle.demoproyecto1BDII.repo;

import com.oracle.demoproyecto1BDII.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findUsuarioByNombreUsuario(String nombreUsuario);
}
