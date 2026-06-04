package com.microusuarios.servicio;

import com.microusuarios.modelo.UsuarioDTO;
import com.microusuarios.persistencia.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

public class UsuarioServicio {

    // SINGLETON
    private static UsuarioServicio instancia;
    private final UsuarioDAO dao;

    private UsuarioServicio() {
        this.dao = new UsuarioDAO();
    }

    public static UsuarioServicio getInstance() {
        if (instancia == null) {
            instancia = new UsuarioServicio();
        }
        return instancia;
    }

    public boolean registrar(UsuarioDTO u) throws SQLException {
        if (dao.emailExiste(u.getEmail())) {
            throw new SQLException("El correo ya está registrado");
        }
        return dao.insertar(u);
    }

    public UsuarioDTO login(String email, String contrasena) throws SQLException {
        UsuarioDTO u = dao.buscarPorEmail(email);
        if (u == null || !u.getContrasena().equals(contrasena)) {
            return null;
        }
        return u;
    }

    public boolean actualizar(UsuarioDTO u) throws SQLException {
        return dao.actualizar(u);
    }

    public boolean eliminar(int id) throws SQLException {
        return dao.eliminar(id);
    }

    public List<UsuarioDTO> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public UsuarioDTO buscarPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }
}