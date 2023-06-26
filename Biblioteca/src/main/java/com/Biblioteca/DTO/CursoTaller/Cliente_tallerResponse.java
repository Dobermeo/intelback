package com.Biblioteca.DTO.CursoTaller;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Getter
public class Cliente_tallerResponse implements Serializable {
    private Long id;
    private Long idTaller;
    private String nombre;
    private String lugar;
    private String descripcion;
    private String responsable;
    private Date fechaInicio;
    private Date fechaMaxInscripcion;
    private Date fechaFin;
    private List<ListaClientesTallerRequest> listaClientesTallerRequests;

}
