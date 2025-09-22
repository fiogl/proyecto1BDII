package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "producto")
// Mapeo de la entidad producto en la BD
public class Producto {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Long id;

    @Column(name="nombre", nullable=false, length=150)
    private String nombre;

    @Column(name="cantidad", nullable=false)
    private Long cantidad;

    @Column(name="porcentaje_mayor", nullable=false, precision=5, scale=2)
    private BigDecimal porcentajeMayor;

    @Column(name="precio_detalle", nullable=false, precision=7, scale=0)
    private BigDecimal precioDetalle;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="id_categoria", nullable=false)
    private Categoria categoria;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="id_marca", nullable=false)
    private Marca marca;
}