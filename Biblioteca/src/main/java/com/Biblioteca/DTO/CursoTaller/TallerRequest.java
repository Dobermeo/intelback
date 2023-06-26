package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TallerRequest implements Serializable {

    private Long id;
    private Long idTaller;
    private String nombre;
    private String lugar;
    private String descripcion;
//    private String observaciones;
    private String responsable;
    private Date fechaInicio;
    private Date fechaMaxInscripcion;
    private Date fechaFin;

}
