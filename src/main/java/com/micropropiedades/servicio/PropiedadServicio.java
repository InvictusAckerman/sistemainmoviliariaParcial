package com.micropropiedades.servicio;

import com.micropropiedades.modelo.Inmueble;
import com.micropropiedades.modelo.PropiedadDTO;
import com.micropropiedades.persistencia.PropiedadDAO;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class PropiedadServicio {

    private PropiedadDAO dao = new PropiedadDAO();
    private BusquedaStrategy estrategia;

    private static final String NOTIF_URL =
        "http://localhost:8080/sistemadeventasinmobiliaria/api/notificaciones";

    public void setEstrategia(BusquedaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void registrar(PropiedadDTO dto) throws SQLException {
        dao.insertar(dto);
    }

    public List<Inmueble> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public Inmueble buscarPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Inmueble> buscar(String criterio) throws SQLException {
        if (estrategia == null)
            throw new IllegalStateException("No se ha definido una estrategia de búsqueda");
        return estrategia.buscar(criterio);
    }

    // Original intacto
    public void actualizar(PropiedadDTO dto) throws SQLException {
        dao.actualizar(dto);
    }

    // ✅ NUEVO - actualiza y notifica
    public void actualizarYNotificar(PropiedadDTO dto, int usuarioDestinoId) throws SQLException {
        dao.actualizar(dto);
        notificarCambioEstado(dto.getId(), usuarioDestinoId, dto.getEstado());
    }

    public void eliminar(int id) throws SQLException {
        dao.eliminar(id);
    }

    private void notificarCambioEstado(int inmuebleId, int usuarioDestinoId, String nuevoEstado) {
        try {
            URL url = new URL(NOTIF_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setDoOutput(true);
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);

            String body = "{"
                + "\"tipo\":\"cambio_estado\","
                + "\"inmueble_id\":" + inmuebleId + ","
                + "\"usuario_destino_id\":" + usuarioDestinoId + ","
                + "\"mensaje\":\"El inmueble " + inmuebleId
                + " ha cambiado su estado a " + nuevoEstado + ".\""
                + "}";

            try (OutputStream os = con.getOutputStream()) {
                os.write(body.getBytes("UTF-8"));
            }

            int status = con.getResponseCode();
            System.out.println("[NotifREST] Status: " + status);
            con.disconnect();

        } catch (Exception e) {
            System.err.println("[NotifREST] Error: " + e.getMessage());
        }
    }
}