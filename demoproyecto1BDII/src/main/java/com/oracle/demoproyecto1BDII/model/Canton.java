package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "canton")
public class Canton {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_canton")
    private Long id;

    @Column(name="nombre", nullable=false, length=30)
    private String nombre;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="id_provincia", nullable=false)
    private Provincia provincia;
}
