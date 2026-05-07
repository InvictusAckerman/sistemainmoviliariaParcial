package com.servicio;

import com.modelo.Usuario;
import com.persistencia.UsuarioRepositorio;
import java.sql.SQLException;
import java.util.List;

public class UsuarioServicio {

    private final UsuarioRepositorio repositorio = new UsuarioRepositorio();

    public boolean registrar(Usuario u) throws Exception {
        if (u.getNombre() == null || u.getNombre().trim().isEmpty())
            throw new Exception("El nombre es obligatorio");
        if (u.getApellido() == null || u.getApellido().trim().isEmpty())
            throw new Exception("El apellido es obligatorio");
        if (u.getEmail() == null || !u.getEmail().contains("@") || !u.getEmail().contains("."))
            throw new Exception("El email no tiene un formato válido");
        if (u.getContrasena() == null || u.getContrasena().trim().length() < 4)
            throw new Exception("La contraseña debe tener al menos 4 caracteres");

        u.setNombre(u.getNombre().trim());
        u.setApellido(u.getApellido().trim());
        u.setEmail(u.getEmail().trim().toLowerCase());
        u.setTelefono(u.getTelefono() != null ? u.getTelefono().trim() : "");
        u.setContrasena(u.getContrasena().trim());

        try {
            return repositorio.insertar(u);
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique"))
                throw new Exception("El email " + u.getEmail() + " ya está registrado");
            throw new Exception("Error de base de datos: " + e.getMessage());
        }
    }

    public Usuario login(String email, String contrasena) throws Exception {
        if (email == null || email.trim().isEmpty())
            throw new Exception("El email es obligatorio");
        if (contrasena == null || contrasena.trim().isEmpty())
            throw new Exception("La contraseña es obligatoria");
        try {
            Usuario u = repositorio.buscarPorEmailYContrasena(
                email.trim().toLowerCase(), contrasena.trim());
            if (u == null)
                throw new Exception("Email o contraseña incorrectos");
            return u;
        } catch (SQLException e) {
            throw new Exception("Error de base de datos: " + e.getMessage());
        }
    }

    public List<Usuario> obtenerTodos() throws Exception {
        try {
            return repositorio.listarTodos();
        } catch (SQLException e) {
            throw new Exception("Error al obtener usuarios: " + e.getMessage());
        }
    }

    public Usuario obtenerPorId(int id) throws Exception {
        try {
            Usuario u = repositorio.buscarPorId(id);
            if (u == null) throw new Exception("Usuario no encontrado con id: " + id);
            return u;
        } catch (SQLException e) {
            throw new Exception("Error al buscar usuario: " + e.getMessage());
        }
    }

    public boolean actualizar(Usuario u) throws Exception {
        if (u.getNombre() == null || u.getNombre().trim().isEmpty())
            throw new Exception("El nombre es obligatorio");
        if (u.getEmail() == null || !u.getEmail().contains("@"))
            throw new Exception("Email inválido");
        try {
            return repositorio.actualizar(u);
        } catch (SQLException e) {
            throw new Exception("Error al actualizar: " + e.getMessage());
        }
    }

    public boolean eliminar(int id) throws Exception {
        try {
            return repositorio.eliminar(id);
        } catch (SQLException e) {
            throw new Exception("Error al eliminar: " + e.getMessage());
        }
    }
}