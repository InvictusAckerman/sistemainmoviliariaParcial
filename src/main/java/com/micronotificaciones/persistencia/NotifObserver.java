package com.micronotificaciones.persistencia;

import com.micronotificaciones.modelo.NotifDTO;


public interface NotifObserver {
    void actualizar(String evento);
}