package com.micronotificaciones.control;

import com.micronotificaciones.modelo.NotifDTO;
import com.micronotificaciones.servicio.NotifServicio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet("/api/notificaciones")
public class NotifServlet extends HttpServlet {

    private NotifServicio servicio;

    @Override
    public void init() throws ServletException {
        servicio = new NotifServicio();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            String usuarioIdParam = req.getParameter("usuarioId");
            List<NotifDTO> lista;

            if (usuarioIdParam != null && !usuarioIdParam.isBlank()) {
                lista = servicio.listarPorUsuario(Integer.parseInt(usuarioIdParam));
            } else {
                lista = servicio.listar();
            }

            out.print(listaToJson(lista));
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String linea;
                while ((linea = reader.readLine()) != null) sb.append(linea);
            }

            NotifDTO dto = parsearJson(sb.toString());
            servicio.actualizar(dto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"mensaje\":\"Notificacion creada\",\"id\":\"" + dto.getId() + "\"}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            String id = req.getParameter("id");
            if (id == null || id.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Se requiere el parametro id\"}");
                return;
            }

            boolean ok = servicio.marcarLeida(id);
            if (ok) {
                resp.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"mensaje\":\"Notificacion marcada como leida\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"No encontrada: " + id + "\"}");
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ── Utilidades ───────────────────────────────────────────

    private String listaToJson(List<NotifDTO> lista) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            json.append(dtoToJson(lista.get(i)));
            if (i < lista.size() - 1) json.append(",");
        }
        return json.append("]").toString();
    }

    private String dtoToJson(NotifDTO dto) {
        return "{"
            + "\"id\":\""                + safe(dto.getId())        + "\","
            + "\"tipo\":\""             + safe(dto.getTipo())       + "\","
            + "\"inmueble_id\":"        + dto.getInmuebleId()       + ","
            + "\"usuario_destino_id\":" + dto.getUsuarioDestinoId() + ","
            + "\"mensaje\":\""          + safe(dto.getMensaje())    + "\","
            + "\"leida\":"              + dto.isLeida()             + ","
            + "\"createdAt\":\""        + (dto.getCreatedAt() != null
                                           ? dto.getCreatedAt() : "") + "\""
            + "}";
    }

    private NotifDTO parsearJson(String json) {
        NotifDTO dto = new NotifDTO();
        dto.setTipo(extraerString(json, "tipo"));
        dto.setInmuebleId(extraerInt(json, "inmueble_id"));
        dto.setUsuarioDestinoId(extraerInt(json, "usuario_destino_id"));
        dto.setMensaje(extraerString(json, "mensaje"));
        dto.setLeida(false);
        dto.setCreatedAt(new Date());
        return dto;
    }

    private String extraerString(String json, String clave) {
        String patron = "\"" + clave + "\"";
        int idx = json.indexOf(patron);
        if (idx < 0) return "";
        int ini = json.indexOf("\"", idx + patron.length() + 1) + 1;
        return json.substring(ini, json.indexOf("\"", ini));
    }

    private int extraerInt(String json, String clave) {
        String patron = "\"" + clave + "\"";
        int idx = json.indexOf(patron);
        if (idx < 0) return 0;
        int ini = idx + patron.length();
        while (ini < json.length() &&
               (json.charAt(ini) == ':' || json.charAt(ini) == ' ')) ini++;
        int fin = ini;
        while (fin < json.length() && Character.isDigit(json.charAt(fin))) fin++;
        try { return Integer.parseInt(json.substring(ini, fin)); }
        catch (NumberFormatException e) { return 0; }
    }

    private String safe(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }
}