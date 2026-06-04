package com.micropropiedades.servicio;

import com.micropropiedades.modelo.PropiedadDTO;
import java.util.List;
import java.util.stream.Collectors;

public class BusquedaPorTipo implements BusquedaStrategy {

    @Override
    public List<PropiedadDTO> buscar(String filtro, List<PropiedadDTO> propiedades) {
        return propiedades.stream()
            .filter(p -> p.getTipo().equalsIgnoreCase(filtro))
            .collect(Collectors.toList());
    }
}