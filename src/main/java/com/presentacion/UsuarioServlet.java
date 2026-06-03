package com.presentacion;

import com.servicio.UsuarioServicio;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class UsuarioServlet extends HttpServlet {

    private UsuarioServicio servicio = new UsuarioServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "formulario";

        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                // ✅ Redirige al HTML ya creado
                response.sendRedirect("login.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if ("actualizar".equals(accion)) {
            actualizar(request, response);
        } else if ("login".equals(accion)) {
            procesarLogin(request, response);
        } else {
            registrar(request, response);
        }
    }

    // ── REGISTRAR ──────────────────────────────────────────
    // ✅ Solo pasa los parámetros al servicio — el objeto se crea allá
    private void registrar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.registrar(
                req.getParameter("nombre"),
                req.getParameter("apellido"),
                req.getParameter("email"),
                req.getParameter("telefono"),
                req.getParameter("tipo_usuario"),
                req.getParameter("contrasena")
            );
            resp.sendRedirect("login.html?exito=Usuario+registrado+exitosamente");
        } catch (Exception e) {
            resp.sendRedirect("login.html?error=" + e.getMessage());
        }
    }

    // ── LOGIN ──────────────────────────────────────────────
    private void procesarLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.login(
                req.getParameter("email"),
                req.getParameter("contrasena")
            );
            resp.sendRedirect("index.html");
        } catch (Exception e) {
            resp.sendRedirect("login.html?error=" + e.getMessage());
        }
    }

    // ── LISTAR ─────────────────────────────────────────────
    // ✅ Pasa datos al JSP — no genera HTML aquí
    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("usuarios", servicio.obtenerTodos());
            req.setAttribute("exito", req.getParameter("exito"));
            req.setAttribute("error", req.getParameter("error"));
            req.getRequestDispatcher("listarUsuarios.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect("login.html?error=" + e.getMessage());
        }
    }

    // ── EDITAR ─────────────────────────────────────────────
    // ✅ Pasa el usuario al JSP — no genera HTML aquí
    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("usuario", servicio.obtenerPorId(id));
            req.getRequestDispatcher("editarUsuario.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?accion=listar&error=" + e.getMessage());
        }
    }

    // ── ACTUALIZAR ─────────────────────────────────────────
    // ✅ Solo pasa parámetros al servicio — el objeto se crea allá
    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.actualizar(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("nombre"),
                req.getParameter("apellido"),
                req.getParameter("email"),
                req.getParameter("telefono"),
                req.getParameter("tipo_usuario"),
                req.getParameter("contrasena")
            );
            resp.sendRedirect("UsuarioServlet?accion=listar&exito=Usuario+actualizado");
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?accion=listar&error=" + e.getMessage());
        }
    }

    // ── ELIMINAR ───────────────────────────────────────────
    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.eliminar(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("UsuarioServlet?accion=listar&exito=Usuario+eliminado");
        } catch (Exception e) {
            resp.sendRedirect("UsuarioServlet?accion=listar&error=" + e.getMessage());
        }
    }
}