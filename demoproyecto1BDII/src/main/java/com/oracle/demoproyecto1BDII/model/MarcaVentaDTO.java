package com.oracle.demoproyecto1BDII.model;

public class MarcaVentaDTO {
    private String marca;
    private int totalVendido;

    public MarcaVentaDTO(String marca, int totalVendido) {
        this.marca = marca;
        this.totalVendido = totalVendido;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getTotalVendido() { return totalVendido; }
    public void setTotalVendido(int totalVendido) { this.totalVendido = totalVendido; }
}
