package com.micropropiedades.modelo;

public class Apartamento extends Inmueble {
    private int piso;

    public Apartamento() {}

    public Apartamento(String titulo, double precio, String estado, int piso) {
        super(titulo, "Apartamento", precio, estado);
        this.piso = piso;
    }

    public int getPiso() { return piso; }
    public void setPiso(int piso) { this.piso = piso; }

    @Override
    public String toString() {
        return super.toString() + ", piso=" + piso + "}";
    }
}