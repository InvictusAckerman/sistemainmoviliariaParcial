package com.micropropiedades.servicio;

import com.micropropiedades.modelo.Inmueble;
import com.micropropiedades.persistencia.PropiedadDAO;
import java.sql.SQLException;
import java.util.List;

public class BusquedaPorPrecio implements BusquedaStrategy {

    private PropiedadDAO dao = new PropiedadDAO();

    @Override
    public List<Inmueble> buscar(String precioMax) throws SQLException {
        double precio = Double.parseDouble(precioMax);
        return dao.buscarPorPrecio(precio);
    }
}