package com.Biblioteca.DTO.Computo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ComputoClienteRequest implements Serializable {

private String descripcion, horaInicio, horaFin;

private Long idCliente, idInventario;

private Date fecha;

}
