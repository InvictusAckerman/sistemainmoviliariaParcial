package com.micronotificaciones.persistencia;

import com.micronotificaciones.modelo.NotifDTO;
import java.util.Date;

// ✅ Patrón Observer — reacciona a eventos de otros microservicios
public class NotifObserver {

    private final NotifDAO dao = new NotifDAO();

    public void actualizar(String evento, int usuarioDestinoId) {
        try {
            NotifDTO notif = new NotifDTO();
            notif.setTipo(evento);
            notif.setMensaje(generarMensaje(evento));
            notif.setLeida(false);
            notif.setCreatedAt(new Date());
            notif.setUsuarioDestinoId(usuarioDestinoId);
            dao.insertar(notif);
        } catch (Exception e) {
            System.err.println("Error al crear notificación: " + e.getMessage());
        }
    }

    private String generarMensaje(String evento) {
        switch (evento) {
            case "registro_usuario":   return "Bienvenido, tu cuenta fue creada exitosamente.";
            case "cambio_estado":      return "El estado de tu inmueble ha cambiado.";
            case "aprobacion":         return "Tu publicación fue aprobada.";
            case "nuevo_inmueble":     return "Se publicó un nuevo inmueble de tu interés.";
            default:                   return "Tienes una nueva notificación.";
        }
    }
}