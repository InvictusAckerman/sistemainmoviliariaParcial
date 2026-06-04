package com.microusuarios.control;

import com.microusuarios.modelo.UsuarioDTO;
import com.microusuarios.servicio.UsuarioServicio;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/UsuarioController")

public class UsuarioController extends HttpServlet {

    private UsuarioServicio servicio;

    @Override
    public void init() {
        servicio = UsuarioServicio.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        try {
            switch (accion) {
                case "listar":
                    List<UsuarioDTO> lista = servicio.listarTodos();
                    req.setAttribute("usuarios", lista);
                    req.getRequestDispatcher("/ListarTodos.jsp").forward(req, resp);
                    break;

                case "editar":
                    int id = Integer.parseInt(req.getParameter("id"));
                    UsuarioDTO u = servicio.buscarPorId(id);
                    req.setAttribute("usuario", u);
                    req.getRequestDispatcher("/FormMonitor.jsp").forward(req, resp);;
                    break;

                case "eliminar":
                    int idElim = Integer.parseInt(req.getParameter("id"));
                    servicio.eliminar(idElim);
                    resp.sendRedirect("usuarios?accion=listar");
                    break;

                default:
                    resp.sendRedirect("index.html");
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("/ListarTodos.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");

        try {
            switch (accion) {
                case "registrar":
                    UsuarioDTO nuevo = new UsuarioDTO();
                    nuevo.setNombre(req.getParameter("nombre"));
                    nuevo.setApellido(req.getParameter("apellido"));
                    nuevo.setEmail(req.getParameter("email"));
                    nuevo.setTelefono(req.getParameter("telefono"));
                    nuevo.setTipoUsuario(req.getParameter("tipoUsuario"));
                    nuevo.setContrasena(req.getParameter("contrasena"));
                    servicio.registrar(nuevo);
                    resp.sendRedirect("login.html?msg=registrado");
                    break;

                case "login":
                    String email = req.getParameter("email");
                    String contrasena = req.getParameter("contrasena");
                    UsuarioDTO u = servicio.login(email, contrasena);
                    if (u != null) {
                        HttpSession session = req.getSession();
                        session.setAttribute("usuarioLogueado", u);
                        resp.sendRedirect("index.html");
                    } else {
                        resp.sendRedirect("login.html?error=credenciales");
                    }
                    break;

                case "actualizar":
                    UsuarioDTO actualizado = new UsuarioDTO();
                    actualizado.setId(Integer.parseInt(req.getParameter("id")));
                    actualizado.setNombre(req.getParameter("nombre"));
                    actualizado.setApellido(req.getParameter("apellido"));
                    actualizado.setTelefono(req.getParameter("telefono"));
                    actualizado.setEmail(req.getParameter("email"));
                    actualizado.setContrasena(req.getParameter("contrasena"));
                    actualizado.setTipoUsuario(req.getParameter("tipoUsuario"));
                    servicio.actualizar(actualizado);
                    resp.sendRedirect("usuarios?accion=listar");
                    break;

                default:
                    resp.sendRedirect("index.html");
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("/login.html").forward(req, resp);
        }
    }
}