package com.micronotificaciones.control;

import com.micronotificaciones.servicio.NotifServicio;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class NotifServlet extends HttpServlet {

    private NotifServicio servicio = new NotifServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("listar".equals(accion)) {
            try {
                request.setAttribute("notificaciones", servicio.listar());
                request.getRequestDispatcher("ListarNotificaciones.jsp")
                       .forward(request, response);
            } catch (Exception e) {
                response.sendRedirect("index.html?error=" + e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String evento = request.getParameter("evento");
            int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
            servicio.enviar(evento, usuarioId);
            response.sendRedirect("NotifServlet?accion=listar&exito=Notificacion+enviada");
        } catch (Exception e) {
            response.sendRedirect("NotifServlet?accion=listar&error=" + e.getMessage());
        }
    }
}