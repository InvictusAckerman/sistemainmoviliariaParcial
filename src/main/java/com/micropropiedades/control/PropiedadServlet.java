package com.micropropiedades.control;

import com.micropropiedades.modelo.Inmueble;
import com.micropropiedades.modelo.PropiedadDTO;
import com.micropropiedades.servicio.BusquedaPorPrecio;
import com.micropropiedades.servicio.BusquedaPorTipo;
import com.micropropiedades.servicio.PropiedadServicio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/propiedades")
public class PropiedadServlet extends HttpServlet {

    private PropiedadServicio servicio = new PropiedadServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String accion    = request.getParameter("accion");
        String criterio  = request.getParameter("criterio");

        try {
            if (accion == null || accion.isEmpty()) {
                // Listar todos
                List<Inmueble> lista = servicio.listarTodos();
                out.print(listaToJson(lista));

            } else if (accion.equals("buscarTipo")) {
                servicio.setEstrategia(new BusquedaPorTipo());
                List<Inmueble> lista = servicio.buscar(criterio);
                out.print(listaToJson(lista));

            } else if (accion.equals("buscarPrecio")) {
                servicio.setEstrategia(new BusquedaPorPrecio());
                List<Inmueble> lista = servicio.buscar(criterio);
                out.print(listaToJson(lista));

            } else {
                response.setStatus(400);
                out.print("{\"error\":\"Acción no reconocida\"}");
            }

        } catch (SQLException e) {
            response.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String accion = request.getParameter("accion");

        try {
            if (accion == null) accion = "registrar";

            switch (accion) {
                case "registrar": {
                    PropiedadDTO dto = new PropiedadDTO();
                    dto.setTitulo(request.getParameter("titulo"));
                    dto.setTipo(request.getParameter("tipo"));
                    dto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                    dto.setEstado(request.getParameter("estado"));

                    if (dto.getTipo().equalsIgnoreCase("Apartamento")) {
                        dto.setPiso(Integer.parseInt(request.getParameter("piso")));
                    } else if (dto.getTipo().equalsIgnoreCase("Casa")) {
                        dto.setJardin(Boolean.parseBoolean(request.getParameter("jardin")));
                    }

                    servicio.registrar(dto);
                    out.print("{\"mensaje\":\"Inmueble registrado correctamente\"}");
                    break;
                }
                case "actualizar": {
                    PropiedadDTO dto = new PropiedadDTO();
                    dto.setId(Integer.parseInt(request.getParameter("id")));
                    dto.setTitulo(request.getParameter("titulo"));
                    dto.setTipo(request.getParameter("tipo"));
                    dto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                    dto.setEstado(request.getParameter("estado"));

                    servicio.actualizar(dto);
                    out.print("{\"mensaje\":\"Inmueble actualizado correctamente\"}");
                    break;
                }
                case "eliminar": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    servicio.eliminar(id);
                    out.print("{\"mensaje\":\"Inmueble eliminado correctamente\"}");
                    break;
                }
                default:
                    response.setStatus(400);
                    out.print("{\"error\":\"Acción no reconocida\"}");
            }

        } catch (SQLException e) {
            response.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private String listaToJson(List<Inmueble> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            Inmueble inm = lista.get(i);
            sb.append("{");
            sb.append("\"id\":").append(inm.getId()).append(",");
            sb.append("\"titulo\":\"").append(inm.getTitulo()).append("\",");
            sb.append("\"tipo\":\"").append(inm.getTipo()).append("\",");
            sb.append("\"precio\":").append(inm.getPrecio()).append(",");
            sb.append("\"estado\":\"").append(inm.getEstado()).append("\"");
            sb.append("}");
            if (i < lista.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}