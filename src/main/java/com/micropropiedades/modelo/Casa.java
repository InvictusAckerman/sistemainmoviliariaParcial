package com.micropropiedades.modelo;

public class Casa extends Inmueble {
    private boolean jardin;

    public Casa() { this.tipo = "casa"; }

    public boolean isJardin() { return jardin; }
    public void setJardin(boolean jardin) { this.jardin = jardin; }
}