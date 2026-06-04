package com.micropropiedades.servicio;

import com.micropropiedades.modelo.PropiedadDTO;
import java.util.List;

public interface BusquedaStrategy {
    List<PropiedadDTO> buscar(String filtro, List<PropiedadDTO> propiedades);
}