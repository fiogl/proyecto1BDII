package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
// Mapeo de la entidad usuario en la BD
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long id;

    @Column(name="nombre_usuario", nullable=false, length=300)
    private String nombreUsuario;

    @Column(name="contrasenia_hash", nullable=false, length=65)
    private String contraseniaHash;
}