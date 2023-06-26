package com.Biblioteca.DTO.CursoTaller.reportes;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
public class TallerporGeneroResponse implements Serializable {

    private Long n_Masculino;
    private Double porcent_Masculino;
    private Long n_Femenino;
    private Double porcent_Femenino;
    private Long n_Otro;
    private Double porcent_Otro;
    private Long total;
}