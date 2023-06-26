package com.Biblioteca.DTO.Servicios.CopiasImpresiones;

import lombok.Data;

import java.io.Serializable;

@Data
public class CopiasImpresionesRequest implements Serializable {

    private Long id, pagBlanco, pagColor, pagTotal;
}
