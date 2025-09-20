package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "provincia")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Long idProvincia;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "provincia")
    private List<Canton> cantones;

    public Provincia() {}
}
