package com.Biblioteca.DTO.Persona;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonaUsuarioRequest implements Serializable {

    private Long id;

    private String cedula;

    private String apellidos;

    private String nombres;

    private String email;

    private String telefono;

    private String clave;

    private Long idRol;
}
