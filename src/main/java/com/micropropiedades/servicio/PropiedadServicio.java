package com.micropropiedades.servicio;

import com.micropropiedades.modelo.Inmueble;
import com.micropropiedades.modelo.PropiedadDTO;
import com.micropropiedades.persistencia.PropiedadDAO;
import java.sql.SQLException;
import java.util.List;

public class PropiedadServicio {

    private PropiedadDAO dao = new PropiedadDAO();
    private BusquedaStrategy estrategia;

    // Permite cambiar la estrategia de búsqueda en tiempo de ejecución
    public void setEstrategia(BusquedaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void registrar(PropiedadDTO dto) throws SQLException {
        dao.insertar(dto);
    }

    public List<Inmueble> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public Inmueble buscarPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Inmueble> buscar(String criterio) throws SQLException {
        if (estrategia == null) {
            throw new IllegalStateException("No se ha definido una estrategia de búsqueda");
        }
        return estrategia.buscar(criterio);
    }

    public void actualizar(PropiedadDTO dto) throws SQLException {
        dao.actualizar(dto);
    }

    public void eliminar(int id) throws SQLException {
        dao.eliminar(id);
    }
}