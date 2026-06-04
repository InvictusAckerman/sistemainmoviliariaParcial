package com.micropropiedades.servicio;

import com.micropropiedades.modelo.Inmueble;
import java.sql.SQLException;
import java.util.List;

public interface BusquedaStrategy {
    List<Inmueble> buscar(String criterio) throws SQLException;
}