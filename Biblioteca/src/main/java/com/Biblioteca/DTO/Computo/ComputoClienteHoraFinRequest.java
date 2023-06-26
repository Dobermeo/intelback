package com.Biblioteca.DTO.Computo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ComputoClienteHoraFinRequest implements Serializable {


    private Long id;
    private String horaFin;
}
