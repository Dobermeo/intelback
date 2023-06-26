package com.Biblioteca.DTO.Ubicacion;

import lombok.Data;

import java.io.Serializable;

@Data
public class BarrioResponse implements Serializable {

    private Long id;

    private String barrio;
}
