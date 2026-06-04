package com.micropropiedades.persistencia;

import com.micropropiedades.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropiedadDAO {

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/inmobiliaria_db",
                "postgres",
                "empanada123"
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver no encontrado", e);
        }
    }

    public boolean insertar(PropiedadDTO p) throws Exception {
        String sql = "INSERT INTO inmuebles (titulo, tipo, precio, ubicacion, estado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getTipo());
            ps.setDouble(3, p.getPrecio());
            ps.setString(4, p.getUbicacion());
            ps.setString(5, p.getEstado());
            return ps.executeUpdate() > 0;
        }
    }

    public List<PropiedadDTO> listarTodos() throws Exception {
        List<PropiedadDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM inmuebles ORDER BY id DESC";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public PropiedadDTO buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM inmuebles WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    public boolean actualizar(PropiedadDTO p) throws Exception {
        String sql = "UPDATE inmuebles SET titulo=?, tipo=?, precio=?, " +
                     "ubicacion=?, estado=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getTipo());
            ps.setDouble(3, p.getPrecio());
            ps.setString(4, p.getUbicacion());
            ps.setString(5, p.getEstado());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM inmuebles WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private PropiedadDTO mapear(ResultSet rs) throws SQLException {
        return new PropiedadDTO(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("tipo"),
            rs.getDouble("precio"),
            rs.getString("ubicacion"),
            rs.getString("estado")
        );
    }
}