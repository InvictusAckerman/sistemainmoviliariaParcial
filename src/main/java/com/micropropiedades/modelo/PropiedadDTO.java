package com.micropropiedades.modelo;

public class PropiedadDTO {
    private int id;
    private String titulo;
    private String tipo;
    private double precio;
    private String ubicacion;
    private String estado;

    public PropiedadDTO() {}

    public PropiedadDTO(int id, String titulo, String tipo,
                        double precio, String ubicacion, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.precio = precio;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}