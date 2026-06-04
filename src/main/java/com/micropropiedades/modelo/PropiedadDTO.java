package com.micropropiedades.modelo;

public class PropiedadDTO {
    private int id;
    private String titulo;
    private String tipo;
    private double precio;
    private String estado;
    private int piso;           // solo para Apartamento
    private boolean jardin;     // solo para Casa

    public PropiedadDTO() {}

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

    public int getPiso() { return piso; }
    public void setPiso(int piso) { this.piso = piso; }

    public boolean isJardin() { return jardin; }
    public void setJardin(boolean jardin) { this.jardin = jardin; }
}