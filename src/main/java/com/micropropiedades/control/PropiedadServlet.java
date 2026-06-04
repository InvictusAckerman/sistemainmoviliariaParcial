package com.micropropiedades.control;

import com.micropropiedades.servicio.PropiedadServicio;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class PropiedadServlet extends HttpServlet {

    private PropiedadServicio servicio = new PropiedadServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";
        switch (accion) {
            case "listar":   listar(request, response);   break;
            case "eliminar": eliminar(request, response); break;
            default:         response.sendRedirect("index.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if ("actualizar".equals(accion)) { actualizar(request, response); return; }
        registrar(request, response);
    }

    private void registrar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.registrar(
                req.getParameter("titulo"),
                req.getParameter("tipo"),
                Double.parseDouble(req.getParameter("precio")),
                req.getParameter("ubicacion"),
                req.getParameter("estado") != null ? req.getParameter("estado") : "disponible"
            );
            resp.sendRedirect("PropiedadServlet?accion=listar&exito=Propiedad+registrada");
        } catch (Exception e) {
            resp.sendRedirect("PropiedadServlet?accion=listar&error=" + e.getMessage());
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("propiedades", servicio.listarTodos());
            req.setAttribute("exito", req.getParameter("exito"));
            req.setAttribute("error", req.getParameter("error"));
            req.getRequestDispatcher("ListarPropiedades.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect("index.html?error=" + e.getMessage());
        }
    }

    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.actualizar(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("titulo"),
                req.getParameter("tipo"),
                Double.parseDouble(req.getParameter("precio")),
                req.getParameter("ubicacion"),
                req.getParameter("estado")
            );
            resp.sendRedirect("PropiedadServlet?accion=listar&exito=Propiedad+actualizada");
        } catch (Exception e) {
            resp.sendRedirect("PropiedadServlet?accion=listar&error=" + e.getMessage());
        }
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            servicio.eliminar(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("PropiedadServlet?accion=listar&exito=Propiedad+eliminada");
        } catch (Exception e) {
            resp.sendRedirect("PropiedadServlet?accion=listar&error=" + e.getMessage());
        }
    }
}