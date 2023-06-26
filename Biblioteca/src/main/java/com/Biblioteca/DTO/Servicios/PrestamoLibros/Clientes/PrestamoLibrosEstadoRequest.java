package com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrestamoLibrosEstadoRequest implements Serializable {

    private Long id;
    private Boolean estado;
    private String nombre;
    private String autor;
    private String isbn;

}
