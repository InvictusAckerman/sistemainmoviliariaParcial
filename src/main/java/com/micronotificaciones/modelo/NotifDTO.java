package com.micronotificaciones.modelo;

import java.util.Date;

public class NotifDTO {
    private String id;
    private String tipo;
    private String mensaje;
    private boolean leida;
    private Date createdAt;
    private int usuarioDestinoId;

    public NotifDTO() {}

    public NotifDTO(String id, String tipo, String mensaje,
                    boolean leida, Date createdAt, int usuarioDestinoId) {
        this.id = id;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.leida = leida;
        this.createdAt = createdAt;
        this.usuarioDestinoId = usuarioDestinoId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public int getUsuarioDestinoId() { return usuarioDestinoId; }
    public void setUsuarioDestinoId(int usuarioDestinoId) { this.usuarioDestinoId = usuarioDestinoId; }
}