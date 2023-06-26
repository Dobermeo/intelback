package com.Biblioteca.DTO.Puntuacion;

import lombok.Data;

@Data
public class PuntuacionRequest {

    private Long id, puntuacion, intento, idJuego, idUsuario;

}
