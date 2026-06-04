package com.micropropiedades.modelo;

public class Apartamento extends Inmueble {
    private int piso;

    public Apartamento() { this.tipo = "apartamento"; }

    public int getPiso() { return piso; }
    public void setPiso(int piso) { this.piso = piso; }
}