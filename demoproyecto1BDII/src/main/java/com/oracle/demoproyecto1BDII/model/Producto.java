package com.oracle.demoproyecto1BDII.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column
    private String nombre;

    @Column
    private Integer cantidad;

    @Column(name = "porcentajeMayor")
    private Double porcentajeMayor;

    @Column(name = "precio_detalle")
    private Double precioDetalle;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;
}
