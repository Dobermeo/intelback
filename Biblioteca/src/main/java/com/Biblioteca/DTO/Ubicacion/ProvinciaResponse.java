package com.Biblioteca.DTO.Ubicacion;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProvinciaResponse implements Serializable {

    private Long id;

    private String provincia;
}
