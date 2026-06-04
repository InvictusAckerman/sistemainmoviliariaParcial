package com.micronotificaciones.servicio;

import com.micronotificaciones.modelo.NotifDTO;
import com.micronotificaciones.persistencia.NotifDAO;
import com.micronotificaciones.persistencia.NotifObserver;
import java.util.List;

public class NotifServicio {

    private final NotifDAO dao = new NotifDAO();
    private final NotifObserver observer = new NotifObserver();

    // Llamado desde ms-usuarios o ms-propiedades
    public void enviar(String evento, int usuarioDestinoId) {
        observer.actualizar(evento, usuarioDestinoId);
    }

    public List<NotifDTO> listar() throws Exception {
        return dao.listarTodas();
    }

    public void marcarLeida(String id) throws Exception {
        dao.marcarLeida(id);
    }
}