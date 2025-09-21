package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.MarcaVentaDTO;
import com.oracle.demoproyecto1BDII.model.VentaClienteDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final JdbcTemplate jdbcTemplate;

    public ConsultaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MarcaVentaDTO> getMarcasMasVendidas() {
        String sql = """
            SELECT m.nombre AS marca,
                   SUM(d.cantidad * d.precio_venta_unidad) AS total_vendido
            FROM detalle_venta d
            INNER JOIN producto p ON p.id_producto = d.id_producto
            INNER JOIN marca m ON m.id_marca = p.id_marca
            GROUP BY m.nombre,d.cantidad,d.precio_venta_unidad
            ORDER BY total_vendido DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new MarcaVentaDTO(
                        rs.getString("marca"),
                        rs.getInt("total_vendido")
                )
        );
    }

    public List<VentaClienteDTO> getVentasPorCliente() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("pk_consultas")        // package de Oracle
                .withFunctionName("ventas_por_cliente") // función
                .returningResultSet("RETURN_VALUE", (rs, rowNum) ->
                        new VentaClienteDTO(
                                rs.getLong("id_cliente"),
                                rs.getString("cliente"),
                                rs.getInt("items_comprados"),
                                rs.getBigDecimal("total_ventas")
                        )
                );

        return jdbcCall.executeFunction(List.class);
    }


}
