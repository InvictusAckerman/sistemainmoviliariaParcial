package com.micropropiedades.persistencia;

import com.micropropiedades.modelo.Apartamento;
import com.micropropiedades.modelo.Casa;
import com.micropropiedades.modelo.Inmueble;

public class InmuebleFactory {

    public static Inmueble crearInmueble(String tipo) {
        switch (tipo.toLowerCase()) {
            case "apartamento":
                return new Apartamento();
            case "casa":
                return new Casa();
            default:
                throw new IllegalArgumentException("Tipo de inmueble no válido: " + tipo);
        }
    }
}