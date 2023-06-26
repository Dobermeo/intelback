package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Getter
public class Cliente_cursoResponse  implements Serializable {
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
    private List<ListaClientesRequest> listaClientesRequests;

}
