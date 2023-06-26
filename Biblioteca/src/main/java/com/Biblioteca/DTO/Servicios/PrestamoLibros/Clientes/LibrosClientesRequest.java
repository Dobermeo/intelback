package com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LibrosClientesRequest implements Serializable {

    private Long idPrestao;

    private Date fechaEntrega;

    private Date fechaDev;
    private Long idCliente;

    private Long idLibro;

    private String observacionesEntrega;

    private String observacionesDev;


}
