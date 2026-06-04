package com.micropropiedades.servicio;

import com.micropropiedades.modelo.Inmueble;
import com.micropropiedades.persistencia.PropiedadDAO;
import java.sql.SQLException;
import java.util.List;

public class BusquedaPorTipo implements BusquedaStrategy {

    private PropiedadDAO dao = new PropiedadDAO();

    @Override
    public List<Inmueble> buscar(String tipo) throws SQLException {
        return dao.buscarPorTipo(tipo);
    }
}