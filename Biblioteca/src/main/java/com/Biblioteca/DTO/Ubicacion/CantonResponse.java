package com.Biblioteca.DTO.Ubicacion;

import lombok.Data;

import java.io.Serializable;

@Data
public class CantonResponse implements Serializable {

    private Long id;

    private String canton;

    private Long idProvincia;
}
