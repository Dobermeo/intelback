package com.Biblioteca.DTO.Puntuacion;

import lombok.Data;

@Data
public class PuntuacionResponse {

    private Long id, puntuacion, intento, idJuego, idUsuario;

    public PuntuacionResponse(Long id, Long puntuacion, Long intento, Long idJuego, Long idUsuario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.intento = intento;
        this.idJuego = idJuego;
        this.idUsuario = idUsuario;
    }
}
