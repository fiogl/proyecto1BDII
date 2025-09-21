package com.oracle.demoproyecto1BDII.service;

import com.oracle.demoproyecto1BDII.model.MarcaVentaDTO;
import com.oracle.demoproyecto1BDII.model.VentaClienteDTO;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String sql = """
        SELECT c.id_cliente AS id_cliente,
               c.nombre || ' ' || c.p_apellido || ' ' || c.s_apellido AS cliente,
               NVL(SUM(d.cantidad), 0) AS items_comprados,
               NVL(SUM(d.cantidad * d.precio_venta_unidad), 0) AS total_ventas
        FROM clienteP c
        LEFT JOIN venta v ON v.id_cliente = c.id_cliente
        LEFT JOIN detalle_venta d ON d.id_venta = v.id_venta
        GROUP BY c.id_cliente, c.nombre, c.p_apellido, c.s_apellido
        ORDER BY NVL(SUM(d.cantidad * d.precio_venta_unidad), 0) DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new VentaClienteDTO(
                        rs.getInt("id_cliente"),
                        rs.getString("cliente"),
                        rs.getInt("items_comprados"),
                        rs.getInt("total_ventas")
                )
        );
    }

}
