package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.Usuario;
import com.oracle.demoproyecto1BDII.repo.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetallesUsuarioService implements UserDetailsService {
    private final UsuarioRepository repo;

    public DetallesUsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        var u = repo.findUsuarioByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

        return User.withUsername(u.getNombreUsuario())
                .password(u.getContrasenia_hash())
                .roles("ADMIN")
                .disabled(false)
                .build();
    }
}
