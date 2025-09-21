package com.oracle.demoproyecto1BDII.DTOs;

import java.math.BigDecimal;

public class MarcaVentaDTO {
    private String marca;
    private int totalVendido;
    private BigDecimal totalRecaudado;

    public MarcaVentaDTO(String marca, int totalVendido, BigDecimal totalRecaudado) {
        this.marca = marca;
        this.totalVendido = totalVendido;
        this.totalRecaudado = totalRecaudado;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getTotalVendido() { return totalVendido; }
    public void setTotalVendido(int totalVendido) { this.totalVendido = totalVendido; }

    public BigDecimal getTotalRecaudado() { return totalRecaudado; }
    public void setTotalRecaudado(BigDecimal totalRecaudado) { this.totalRecaudado = totalRecaudado; }
}
