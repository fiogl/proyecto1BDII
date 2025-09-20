package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "marca")
public class Marca {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_marca")
    private Long id;

    @Column(name="nombre", nullable=false, length=30, unique=true)
    private String nombre;
}