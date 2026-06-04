package com.micronotificaciones.modelo;

import java.util.Date;

public class NotifDTO {
    private String id;
    private String tipo;
    private int inmuebleId;
    private int usuarioDestinoId;
    private String mensaje;
    private boolean leida;
    private Date createdAt;

    public NotifDTO() {}

    public NotifDTO(String tipo, int inmuebleId, int usuarioDestinoId, String mensaje) {
        this.tipo = tipo;
        this.inmuebleId = inmuebleId;
        this.usuarioDestinoId = usuarioDestinoId;
        this.mensaje = mensaje;
        this.leida = false;
        this.createdAt = new Date();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getInmuebleId() { return inmuebleId; }
    public void setInmuebleId(int inmuebleId) { this.inmuebleId = inmuebleId; }
    public int getUsuarioDestinoId() { return usuarioDestinoId; }
    public void setUsuarioDestinoId(int id) { this.usuarioDestinoId = id; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}