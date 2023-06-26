package com.Biblioteca.DTO.Computo;

import lombok.Data;

import java.io.Serializable;


@Data
public class InventarioRequest implements Serializable {

    private Long id;

    private Long numero;

    private String ram;

    private String discoDuro;

    private String procesador;

    private Boolean estado;

    private Boolean estadoPrestamo;

    private String observacionesComputador;

}
