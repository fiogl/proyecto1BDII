    package com.oracle.demoproyecto1BDII.model;

    import jakarta.persistence.*;
    import lombok.Data;

    @Entity
    @Data
    @Table(name = "clienteP")

    public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_cliente")
        private Long idCliente;

        @Column
        private String nombre;

        @Column(name = "p_apellido")
        private String pApellido;

        @Column(name = "s_apellido")
        private String sApellido;

        @Column(name = "direccion")
        private String direccionExacta;

        @Column
        private String email;

        @Column
        private String telefono;

        @Column(name = "codigo_mayorista")
        private String codigoMayorista;

        @ManyToOne
        @JoinColumn(name = "id_canton")
        private Canton canton;
    }
