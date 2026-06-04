package com.micropropiedades.modelo;

public class Casa extends Inmueble {
    private boolean jardin;

    public Casa() {}

    public Casa(String titulo, double precio, String estado, boolean jardin) {
        super(titulo, "Casa", precio, estado);
        this.jardin = jardin;
    }

    public boolean isJardin() { return jardin; }
    public void setJardin(boolean jardin) { this.jardin = jardin; }

    @Override
    public String toString() {
        return super.toString() + ", jardin=" + jardin + "}";
    }
}