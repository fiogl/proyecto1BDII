package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "detalle_venta")

public class DetalleVenta {
    @EmbeddedId
    private DetalleVentaId id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("idVenta")
    @JoinColumn(name="id_venta", nullable=false)
    private Venta venta;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("idProducto")
    @JoinColumn(name="id_producto", nullable=false)
    private Producto producto;

    @Column(name="cantidad", nullable=false)
    private Long cantidad;

    @Column(name="precio_venta_unidad", precision=7, scale=0, nullable=false)
    private BigDecimal precioVentaUnidad;
}