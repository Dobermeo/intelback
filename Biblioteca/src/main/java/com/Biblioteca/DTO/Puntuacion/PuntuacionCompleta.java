package com.Biblioteca.DTO.Puntuacion;

import lombok.Data;

@Data
public class PuntuacionCompleta {

    private Long puntuacion, intento, idJuego, idUsuario;

    private String nombreUsuario, nombreJuego;

}
