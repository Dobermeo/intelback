package com.Biblioteca.DTO.Persona;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PersonaUsuarioResponse implements Serializable {

    private Long id;

    private Long idUsuario;
    private String cedula;

    private String apellidos;

    private String nombres;

    private String email;

    private String telefono;

    private String clave;

    private Long idRol;

    private String token;

    public PersonaUsuarioResponse(Long id, Long idUsuario, String cedula, String apellidos, String nombres,  String email, String telefono, String clave, Long idRol,String token) {
        this.id = id;
        this.idUsuario =idUsuario;
        this.cedula = cedula;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.email = email;
        this.telefono = telefono;
        this.clave = clave;
        this.idRol = idRol;
        this.token = token;
    }

    public PersonaUsuarioResponse() {

    }
}
