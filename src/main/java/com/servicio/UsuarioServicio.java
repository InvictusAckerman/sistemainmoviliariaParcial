package com.servicio;

import com.modelo.Usuario;
import com.persistencia.UsuarioRepositorio;
import java.sql.SQLException;
import java.util.List;

public class UsuarioServicio {

    private final UsuarioRepositorio repositorio = new UsuarioRepositorio();

    // ✅ Recibe strings y crea el objeto Usuario aquí
    public boolean registrar(String nombre, String apellido, String email,
                             String telefono, String tipoUsuario,
                             String contrasena) throws Exception {

        if (nombre == null || nombre.trim().isEmpty())
            throw new Exception("El nombre es obligatorio");
        if (apellido == null || apellido.trim().isEmpty())
            throw new Exception("El apellido es obligatorio");
        if (email == null || !email.contains("@") || !email.contains("."))
            throw new Exception("El email no tiene un formato válido");
        if (contrasena == null || contrasena.trim().length() < 4)
            throw new Exception("La contraseña debe tener mínimo 4 caracteres");

        // ✅ El objeto Usuario se crea en el SERVICIO
        Usuario u = new Usuario();
        u.setNombre(nombre.trim());
        u.setApellido(apellido.trim());
        u.setEmail(email.trim().toLowerCase());
        u.setTelefono(telefono != null ? telefono.trim() : "");
        u.setTipoUsuario(tipoUsuario != null ? tipoUsuario : "comprador");
        u.setContrasena(contrasena.trim());

        try {
            return repositorio.insertar(u);
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique"))
                throw new Exception("El email " + email + " ya está registrado");
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
            if (u == null) throw new Exception("Usuario no encontrado");
            return u;
        } catch (SQLException e) {
            throw new Exception("Error al buscar usuario: " + e.getMessage());
        }
    }

    // ✅ Recibe parámetros sueltos y crea el objeto Usuario aquí
    public boolean actualizar(int id, String nombre, String apellido,
                              String email, String telefono,
                              String tipoUsuario, String contrasena) throws Exception {

        if (nombre == null || nombre.trim().isEmpty())
            throw new Exception("El nombre es obligatorio");
        if (email == null || !email.contains("@"))
            throw new Exception("Email inválido");

        // ✅ El objeto Usuario se crea en el SERVICIO
        Usuario u = new Usuario();
        u.setId(id);
        u.setNombre(nombre.trim());
        u.setApellido(apellido.trim());
        u.setEmail(email.trim().toLowerCase());
        u.setTelefono(telefono != null ? telefono.trim() : "");
        u.setTipoUsuario(tipoUsuario);
        u.setContrasena(contrasena != null ? contrasena.trim() : "");

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