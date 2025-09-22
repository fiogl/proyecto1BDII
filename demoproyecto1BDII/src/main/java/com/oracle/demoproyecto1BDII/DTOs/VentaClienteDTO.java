package com.oracle.demoproyecto1BDII.DTOs;

import java.math.BigDecimal;

//El cuerpo de la consulta Ventas por cliente
public class VentaClienteDTO {
    private long idCliente;
    private String cliente;
    private BigDecimal totalVendido;
    private int itemsComprados;

    public VentaClienteDTO(long idCliente, String cliente,int itemsComprados, BigDecimal totalVendido) {
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.totalVendido = totalVendido;
        this.itemsComprados = itemsComprados;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(BigDecimal totalVendido) {
        this.totalVendido = totalVendido;
    }

    public int getItemsComprados() {
        return itemsComprados;
    }

    public void setItemsComprados(int itemsComprados) {
        this.itemsComprados = itemsComprados;
    }
}
