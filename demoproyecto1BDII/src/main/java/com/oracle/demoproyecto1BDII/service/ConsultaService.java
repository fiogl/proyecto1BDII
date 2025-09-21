package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.DTOs.MarcaVentaDTO;
import com.oracle.demoproyecto1BDII.DTOs.VentaClienteDTO;
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
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("pk_consultas")
                .withProcedureName("pr_marcas_mas_vendidas")
                .returningResultSet("m_cursor", (rs, rowNum) ->
                        new MarcaVentaDTO(
                                rs.getString("MARCA"),
                                rs.getInt("TOTAL_VENDIDO"),
                                rs.getBigDecimal("TOTAL_RECAUDADO")
                        )
                );

        return (List<MarcaVentaDTO>) jdbcCall.execute().get("m_cursor");
    }

    public List<VentaClienteDTO> getVentasPorCliente() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("pk_consultas")        // package de Oracle
                .withFunctionName("ventas_por_cliente") // función
                .returningResultSet("RETURN_VALUE", (rs, rowNum) ->
                        new VentaClienteDTO(
                                rs.getLong("ID_CLIENTE"),
                                rs.getString("CLIENTE"),
                                rs.getInt("TOTAL_VENTAS"),
                                rs.getBigDecimal("MONTO_TOTAL")
                        )
                );

        return jdbcCall.executeFunction(List.class);
    }


}
