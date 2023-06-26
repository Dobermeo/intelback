package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;

import java.io.Serializable;


@Data
public class ListaClientesTallerRequest implements Serializable {
    private Long id;
    private String cedula;
    private String apellidos;
    private String nombres;
    private String genero;
    private String telefono;
    private String estadoCivil;

}