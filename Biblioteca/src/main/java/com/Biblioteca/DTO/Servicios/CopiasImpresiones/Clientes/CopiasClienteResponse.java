package com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CopiasClienteResponse implements Serializable {
    private Long id;

    private Date fechaEntrega;

    private Long idCliente;
    private String cedula, nombre,apellido;
    private Long idCopias;

    private Long pagBlanco, pagColor, pagTotal;

    public CopiasClienteResponse(Long id,Date fechaEntrega, Long idCliente, Long idCopias, Long pagBlanco, Long pagColor, Long pagTotal) {
        this.id=id;
        this.fechaEntrega = fechaEntrega;
        this.idCliente = idCliente;
        this.idCopias = idCopias;
        this.pagBlanco = pagBlanco;
        this.pagColor = pagColor;
        this.pagTotal = pagTotal;
    }

    public CopiasClienteResponse() {

    }
}
