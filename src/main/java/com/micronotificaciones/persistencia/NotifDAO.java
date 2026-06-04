package com.micronotificaciones.persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.micronotificaciones.modelo.NotifDTO;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotifDAO {

    private MongoCollection<Document> coleccion;

    public NotifDAO() {
        this.coleccion = MongoConexion.getInstance().getColeccion();
    }

    public void insertar(NotifDTO notif) {
        Document doc = new Document()
            .append("tipo",               notif.getTipo())
            .append("inmueble_id",        notif.getInmuebleId())
            .append("usuario_destino_id", notif.getUsuarioDestinoId())
            .append("mensaje",            notif.getMensaje())
            .append("leida",              false)
            .append("createdAt",          notif.getCreatedAt() != null
                                          ? notif.getCreatedAt() : new Date());
        coleccion.insertOne(doc);
        notif.setId(doc.getObjectId("_id").toHexString());
    }

    public List<NotifDTO> listarPorUsuario(int usuarioDestinoId) {
        List<NotifDTO> lista = new ArrayList<>();
        coleccion.find(Filters.eq("usuario_destino_id", usuarioDestinoId))
                 .forEach(doc -> lista.add(documentToDTO(doc)));
        return lista;
    }

    public boolean marcarLeida(String id) {
        var resultado = coleccion.updateOne(
            Filters.eq("_id", new ObjectId(id)),
            Updates.set("leida", true)
        );
        return resultado.getModifiedCount() > 0;
    }

    public List<NotifDTO> listarTodas() {
        List<NotifDTO> lista = new ArrayList<>();
        coleccion.find().forEach(doc -> lista.add(documentToDTO(doc)));
        return lista;
    }

    private NotifDTO documentToDTO(Document doc) {
        NotifDTO dto = new NotifDTO();
        dto.setId(doc.getObjectId("_id").toHexString());
        dto.setTipo(doc.getString("tipo"));
        dto.setInmuebleId(doc.getInteger("inmueble_id", 0));
        dto.setUsuarioDestinoId(doc.getInteger("usuario_destino_id", 0));
        dto.setMensaje(doc.getString("mensaje"));
        dto.setLeida(doc.getBoolean("leida", false));
        dto.setCreatedAt(doc.getDate("createdAt"));
        return dto;
    }
}