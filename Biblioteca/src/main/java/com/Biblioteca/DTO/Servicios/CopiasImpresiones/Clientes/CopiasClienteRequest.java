package com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CopiasClienteRequest implements Serializable {
    private Long id;
    private Date fecha;
    private Long idCliente;
    private Long idCopia;
    private Long pagBlanco, pagColor;
}
