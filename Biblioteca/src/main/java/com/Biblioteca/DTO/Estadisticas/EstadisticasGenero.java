package com.Biblioteca.DTO.Estadisticas;

import lombok.Data;

import java.io.Serializable;

@Data
public class EstadisticasGenero implements Serializable {

    private Long mes;

    private Long anio;

    private Long total;

    private Datos masculino;

    private Datos femenino;

    private Datos otros;
}
