package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "canton")
public class Canton {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canton")
    private Long idCanton;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    @OneToMany(mappedBy = "canton")
    private List<Cliente> clientes;

    public Canton() {}
}
