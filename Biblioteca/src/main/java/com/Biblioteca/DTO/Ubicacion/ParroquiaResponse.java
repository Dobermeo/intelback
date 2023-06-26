package com.Biblioteca.DTO.Ubicacion;

import lombok.Data;

import java.io.Serializable;
@Data
public class ParroquiaResponse implements Serializable {

    private Long id;

    private String parroquia;

    private Long idCanton;
}
