package com.oracle.demoproyecto1BDII.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clienteP")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nombre;
    private String p_Apellido;
    private String s_Apellido;
    private String direccion_exacta;
    private String email;
    private String telefono;
    private String codigo_mayorista;

    @ManyToOne
    @JoinColumn(name = "id_canton")
    private Canton canton;
}
