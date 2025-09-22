package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "provincia")
// Mapeo de la entidad provincia en la BD
public class Provincia {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_provincia")
    private Long id;

    @Column(name="nombre", nullable=false, length=30)
    private String nombre;
}