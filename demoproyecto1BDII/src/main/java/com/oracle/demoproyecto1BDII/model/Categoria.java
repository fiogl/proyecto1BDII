package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

// Mapeo de la entidad categoría en la BD
@Entity
@Data
@Table(name="categoria")
public class Categoria {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Long id;

    @Column(name="nombre", nullable=false, length=30, unique=true)
    private String nombre;
}
