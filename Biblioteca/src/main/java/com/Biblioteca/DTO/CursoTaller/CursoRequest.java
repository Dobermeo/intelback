package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
public class CursoRequest implements Serializable {
    private Long id;
    private Long idCurso;
    private String nombre;
    private String lugar;
    private String descripcion;
//    private String observaciones;
    private String responsable;
    private Date fechaInicio;
    private Date fechaMaxInscripcion;
    private Date fechaFin;
    private String materiales;
    private Long numParticipantes;
    private String actividades;
}
