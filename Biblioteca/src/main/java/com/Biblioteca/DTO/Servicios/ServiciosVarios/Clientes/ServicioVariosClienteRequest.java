package com.Biblioteca.DTO.Servicios.ServiciosVarios.Clientes;

import lombok.Data;

import java.util.Date;

@Data
public class ServicioVariosClienteRequest {

    private Long id;

    private Long idCliente;

    private Long idServicioVario;

    private Date fechaActual;

    private String observaciones;
}
