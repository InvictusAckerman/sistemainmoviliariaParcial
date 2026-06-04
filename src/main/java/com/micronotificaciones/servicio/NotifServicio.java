package com.micronotificaciones.servicio;

import com.micronotificaciones.modelo.NotifDTO;
import com.micronotificaciones.persistencia.NotifDAO;
import com.micronotificaciones.persistencia.NotifObserver;
import java.util.List;

public class NotifServicio implements NotifObserver {

    private final NotifDAO notifDAO;

    public NotifServicio() {
        this.notifDAO = new NotifDAO();
    }

    @Override
    public void actualizar(String evento) {
        String[] partes = evento.split(":", 4);
        if (partes.length < 4)
            throw new IllegalArgumentException("Formato inválido: " + evento);

        NotifDTO dto = new NotifDTO(
            partes[0].trim(),
            Integer.parseInt(partes[1].trim()),
            Integer.parseInt(partes[2].trim()),
            partes[3].trim()
        );
        notifDAO.insertar(dto);
    }

    public void actualizar(NotifDTO dto) {
        notifDAO.insertar(dto);
    }

    public void enviar(NotifDTO dto) {
        notifDAO.insertar(dto);
    }

    public List<NotifDTO> listar() {
        return notifDAO.listarTodas();
    }

    public List<NotifDTO> listarPorUsuario(int usuarioDestinoId) {
        return notifDAO.listarPorUsuario(usuarioDestinoId);
    }

    public boolean marcarLeida(String id) {
        return notifDAO.marcarLeida(id);
    }
}