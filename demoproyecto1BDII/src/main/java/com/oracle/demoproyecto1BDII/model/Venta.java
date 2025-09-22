package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "venta")
// Mapeo de la entidad venta en la BD
public class Venta {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_venta")
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="id_cliente", nullable=false)
    private Cliente cliente;

    @Column(name="fecha", nullable=false)
    private LocalDateTime fecha;

    @Column(name="mensaje", length=1000)
    private String mensaje;

    @OneToMany(mappedBy="venta", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<DetalleVenta> detalles = new ArrayList<>();
}