    package com.oracle.demoproyecto1BDII.model;

    import jakarta.persistence.*;
    import lombok.Data;

    // Mapeo de la entidad cliente en la BD
    @Entity
    @Data
    @Table(name = "clienteP")
    public class Cliente {
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name="id_cliente")
        private Long id;

        @Column(name="nombre", nullable=false, length=40)
        private String nombre;

        @Column(name="p_apellido", nullable=false, length=40)
        private String primerApellido;

        @Column(name="s_apellido", nullable=false, length=40)
        private String segundoApellido;

        @Column(name="direccion", nullable=false, length=200)
        private String direccion;

        @Column(name="email", nullable=false, length=100, unique=true)
        private String email;

        @Column(name="telefono", nullable=false, length=15)
        private String telefono;

        @Column(name="codigo_mayorista", length=40)
        private String codigoMayorista;

        @ManyToOne(optional=false, fetch=FetchType.LAZY)
        @JoinColumn(name="id_canton", nullable=false)
        private Canton canton;
    }
