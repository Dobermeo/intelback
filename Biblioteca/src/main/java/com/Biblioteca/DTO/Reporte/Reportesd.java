package com.Biblioteca.DTO.Reporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Reportesd implements Serializable {
        private String codigo;
        private Long no;
        private String fecha;

        private String cedula;

        private String nombres;
        private String apellidos;
        private String genero;


        private Date fecha_nacimiento;
        private Long edad;

        private String estado_civil;
        private String provincia;
        private String canton;
        private String parroquia;
        private String barrio;
        private Boolean discapacidad;
        private String email;
        private String telefono;
        private Long repositorio;
        private Long biblioteca;
        private Long copias;
        private Long computo;
        private Long talleres;

        private String nombretaller;
        private String verificables;

    }
