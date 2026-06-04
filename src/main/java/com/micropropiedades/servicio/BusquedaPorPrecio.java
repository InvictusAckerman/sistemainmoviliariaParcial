package com.micropropiedades.servicio;

import com.micropropiedades.modelo.PropiedadDTO;
import java.util.List;
import java.util.stream.Collectors;

public class BusquedaPorPrecio implements BusquedaStrategy {

    @Override
    public List<PropiedadDTO> buscar(String filtro, List<PropiedadDTO> propiedades) {
        double maxPrecio = Double.parseDouble(filtro);
        return propiedades.stream()
            .filter(p -> p.getPrecio() <= maxPrecio)
            .collect(Collectors.toList());
    }
}