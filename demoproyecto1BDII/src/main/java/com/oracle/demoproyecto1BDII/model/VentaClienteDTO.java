package com.oracle.demoproyecto1BDII.model;

public class VentaClienteDTO {
    private int idCliente;
    private String cliente;
    private int totalVendido;
    private int itemsComprados;

    public VentaClienteDTO(int idCliente, String cliente,int itemsComprados, int totalVendido) {
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.totalVendido = totalVendido;
        this.itemsComprados = itemsComprados;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(int totalVendido) {
        this.totalVendido = totalVendido;
    }

    public int getItemsComprados() {
        return itemsComprados;
    }

    public void setItemsComprados(int itemsComprados) {
        this.itemsComprados = itemsComprados;
    }
}
