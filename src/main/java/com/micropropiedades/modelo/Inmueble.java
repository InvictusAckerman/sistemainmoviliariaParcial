package com.micropropiedades.modelo;

public abstract class Inmueble {
    private int id;
    private String titulo;
    private String tipo;
    private double precio;
    private String estado;

    public Inmueble() {}

    public Inmueble(String titulo, String tipo, double precio, String estado) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.precio = precio;
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

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Inmueble{id=" + id + ", titulo=" + titulo + ", tipo=" + tipo +
               ", precio=" + precio + ", estado=" + estado + "}";
    }
}