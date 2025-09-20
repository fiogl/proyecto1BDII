package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "venta")

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;

    @Column
    private LocalDateTime fecha;

    @Column
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
