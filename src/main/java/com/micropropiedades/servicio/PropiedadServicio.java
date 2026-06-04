package com.micropropiedades.servicio;

import com.micropropiedades.modelo.*;
import com.micropropiedades.persistencia.*;
import java.util.List;

public class PropiedadServicio {

    private final PropiedadDAO dao = new PropiedadDAO();

    public boolean registrar(String titulo, String tipo, double precio,
                             String ubicacion, String estado) throws Exception {
        // ✅ Factory crea el tipo correcto
        Inmueble inmueble = InmuebleFactory.crearInmueble(tipo);
        inmueble.setTitulo(titulo);
        inmueble.setPrecio(precio);
        inmueble.setUbicacion(ubicacion);
        inmueble.setEstado(estado);

        PropiedadDTO dto = new PropiedadDTO();
        dto.setTitulo(inmueble.getTitulo());
        dto.setTipo(inmueble.getTipo());
        dto.setPrecio(inmueble.getPrecio());
        dto.setUbicacion(inmueble.getUbicacion());
        dto.setEstado(inmueble.getEstado());

        return dao.insertar(dto);
    }

    public List<PropiedadDTO> listarTodos() throws Exception {
        return dao.listarTodos();
    }

    // ✅ Strategy — elige algoritmo de búsqueda según filtro
    public List<PropiedadDTO> buscar(String tipoBusqueda, String valor) throws Exception {
        List<PropiedadDTO> todas = dao.listarTodos();
        BusquedaStrategy strategy;

        switch (tipoBusqueda) {
            case "precio": strategy = new BusquedaPorPrecio(); break;
            case "tipo":   strategy = new BusquedaPorTipo();   break;
            default:       return todas;
        }
        return strategy.buscar(valor, todas);
    }

    public boolean actualizar(int id, String titulo, String tipo,
                              double precio, String ubicacion,
                              String estado) throws Exception {
        PropiedadDTO dto = new PropiedadDTO(id, titulo, tipo, precio, ubicacion, estado);
        return dao.actualizar(dto);
    }

    public boolean eliminar(int id) throws Exception {
        return dao.eliminar(id);
    }
}