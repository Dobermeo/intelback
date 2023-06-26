package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Data
@Getter
public class CursoResponse implements Serializable {
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
    private String actividades;
    private Long numParticipantes;



    ////crear curso
    public CursoResponse(Long id,Long idCurso, String nombre, String lugar, String descripcion,String responsable, Date fechaInicio,  Date fechaMaxInscripcion, Date fechaFin, String actividades, String materiales, Long numParticipantes) {
    this.id=id;
    this.idCurso=idCurso;
    this.nombre=nombre;
    this.lugar=lugar;
    this.descripcion=descripcion;
    this.responsable=responsable;
    this.fechaInicio=fechaInicio;
    this.fechaMaxInscripcion=fechaMaxInscripcion;
    this.fechaFin=fechaFin;
    this.materiales=materiales;
    this.actividades=actividades;
    this.numParticipantes=numParticipantes;
    }
    public CursoResponse() {
    }

    public CursoResponse(Long id, String nombre, String lugar) {
        this.id=id;
        this.nombre=nombre;
        this.lugar=lugar;
    }
}
