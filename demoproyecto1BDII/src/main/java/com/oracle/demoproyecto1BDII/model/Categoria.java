package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categoría")

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre")
    private String nombre;
}
