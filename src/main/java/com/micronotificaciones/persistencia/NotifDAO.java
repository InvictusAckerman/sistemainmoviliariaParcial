package com.micronotificaciones.persistencia;

import com.micronotificaciones.modelo.NotifDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// ✅ Simula MongoDB — en producción usaría MongoClient
public class NotifDAO {

    // Lista en memoria simulando MongoDB
    private static List<NotifDTO> coleccion = new ArrayList<>();
    private static int contador = 1;

    public void insertar(NotifDTO n) throws Exception {
        n.setId("notif_" + contador++);
        coleccion.add(n);
    }

    public List<NotifDTO> listarPorUsuario(int usuarioId) throws Exception {
        List<NotifDTO> resultado = new ArrayList<>();
        for (NotifDTO n : coleccion) {
            if (n.getUsuarioDestinoId() == usuarioId) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    public void marcarLeida(String id) throws Exception {
        for (NotifDTO n : coleccion) {
            if (n.getId().equals(id)) {
                n.setLeida(true);
                break;
            }
        }
    }

    public List<NotifDTO> listarTodas() throws Exception {
        return new ArrayList<>(coleccion);
    }
}