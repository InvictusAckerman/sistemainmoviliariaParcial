package com.micropropiedades.persistencia;

import com.micropropiedades.modelo.Apartamento;
import com.micropropiedades.modelo.Casa;
import com.micropropiedades.modelo.Inmueble;
import com.micropropiedades.modelo.PropiedadDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropiedadDAO {

    
    public void insertar(PropiedadDTO dto) throws SQLException {
        String sqlInmueble = "INSERT INTO inmuebles(titulo, tipo, precio, estado) VALUES (?,?,?,?) RETURNING id";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sqlInmueble)) {

            ps.setString(1, dto.getTitulo());
            ps.setString(2, dto.getTipo());
            ps.setDouble(3, dto.getPrecio());
            ps.setString(4, dto.getEstado());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idGenerado = rs.getInt(1);

                if (dto.getTipo().equalsIgnoreCase("Apartamento")) {
                    String sqlApto = "INSERT INTO apartamentos(id, piso) VALUES (?,?)";
                    try (PreparedStatement ps2 = con.prepareStatement(sqlApto)) {
                        ps2.setInt(1, idGenerado);
                        ps2.setInt(2, dto.getPiso());
                        ps2.executeUpdate();
                    }
                } else if (dto.getTipo().equalsIgnoreCase("Casa")) {
                    String sqlCasa = "INSERT INTO casas(id, jardin) VALUES (?,?)";
                    try (PreparedStatement ps2 = con.prepareStatement(sqlCasa)) {
                        ps2.setInt(1, idGenerado);
                        ps2.setBoolean(2, dto.isJardin());
                        ps2.executeUpdate();
                    }
                }
            }
        }
    }

    
    public List<Inmueble> listarTodos() throws SQLException {
        List<Inmueble> lista = new ArrayList<>();
        String sql = "SELECT i.*, a.piso, c.jardin FROM inmuebles i " +
                     "LEFT JOIN apartamentos a ON i.id = a.id " +
                     "LEFT JOIN casas c ON i.id = c.id";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearInmueble(rs));
            }
        }
        return lista;
    }

    
    public Inmueble buscarPorId(int id) throws SQLException {
        String sql = "SELECT i.*, a.piso, c.jardin FROM inmuebles i " +
                     "LEFT JOIN apartamentos a ON i.id = a.id " +
                     "LEFT JOIN casas c ON i.id = c.id " +
                     "WHERE i.id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapearInmueble(rs);
        }
        return null;
    }

    
    public List<Inmueble> buscarPorTipo(String tipo) throws SQLException {
        List<Inmueble> lista = new ArrayList<>();
        String sql = "SELECT i.*, a.piso, c.jardin FROM inmuebles i " +
                     "LEFT JOIN apartamentos a ON i.id = a.id " +
                     "LEFT JOIN casas c ON i.id = c.id " +
                     "WHERE LOWER(i.tipo) = LOWER(?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapearInmueble(rs));
        }
        return lista;
    }

    
    public List<Inmueble> buscarPorPrecio(double precioMax) throws SQLException {
        List<Inmueble> lista = new ArrayList<>();
        String sql = "SELECT i.*, a.piso, c.jardin FROM inmuebles i " +
                     "LEFT JOIN apartamentos a ON i.id = a.id " +
                     "LEFT JOIN casas c ON i.id = c.id " +
                     "WHERE i.precio <= ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, precioMax);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapearInmueble(rs));
        }
        return lista;
    }

    
    public void actualizar(PropiedadDTO dto) throws SQLException {
        String sql = "UPDATE inmuebles SET titulo=?, tipo=?, precio=?, estado=? WHERE id=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dto.getTitulo());
            ps.setString(2, dto.getTipo());
            ps.setDouble(3, dto.getPrecio());
            ps.setString(4, dto.getEstado());
            ps.setInt(5, dto.getId());
            ps.executeUpdate();
        }
    }

    
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM inmuebles WHERE id=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    
    private Inmueble mapearInmueble(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        Inmueble inmueble;

        if (tipo.equalsIgnoreCase("Apartamento")) {
            Apartamento a = new Apartamento();
            a.setPiso(rs.getInt("piso"));
            inmueble = a;
        } else {
            Casa c = new Casa();
            c.setJardin(rs.getBoolean("jardin"));
            inmueble = c;
        }

        inmueble.setId(rs.getInt("id"));
        inmueble.setTitulo(rs.getString("titulo"));
        inmueble.setTipo(tipo);
        inmueble.setPrecio(rs.getDouble("precio"));
        inmueble.setEstado(rs.getString("estado"));

        return inmueble;
    }
}