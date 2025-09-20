package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "marca")

public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private Long id_marca;

    @Column
    private String nombre;
}
