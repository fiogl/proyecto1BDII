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

        private String nombre;
        private String pApellido;
        private String sApellido;
        private String direccionExacta;
        private String email;
        private String telefono;
        private String codigoMayorista;

        @ManyToOne
        @JoinColumn(name = "canton")
        private Canton canton;
    }
