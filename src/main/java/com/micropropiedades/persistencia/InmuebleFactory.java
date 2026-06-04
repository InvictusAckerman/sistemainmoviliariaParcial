package com.micropropiedades.persistencia;

import com.micropropiedades.modelo.*;

public class InmuebleFactory {

    // ✅ Patrón Factory — crea el tipo correcto de inmueble
    public static Inmueble crearInmueble(String tipo) {
        switch (tipo.toLowerCase()) {
            case "casa":        return new Casa();
            case "apartamento": return new Apartamento();
            default:            return new Inmueble();
        }
    }
}