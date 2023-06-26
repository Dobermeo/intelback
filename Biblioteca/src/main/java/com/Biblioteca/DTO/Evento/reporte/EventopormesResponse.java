package com.Biblioteca.DTO.Evento.reporte;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class EventopormesResponse implements Serializable {
    private Long id;
    private String descripcion;
    private Date fecha;
    private Long numParticipantes;
}
