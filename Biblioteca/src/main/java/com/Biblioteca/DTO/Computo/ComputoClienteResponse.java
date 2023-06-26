package com.Biblioteca.DTO.Computo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ComputoClienteResponse implements Serializable {


    private Long idPrestamo;

    private Long idPersona;

    private Long idCliente;

    private String descripcion, horaInicio, horaFin;

    private String cedula;

    private String apellidos;

    private String nombres;

    private Date fechaNacimiento;

    private Long edad;

    private String genero;

    private String telefono;

    private String email;

    private String estadoCivil;

    private Boolean discapacidad;

    private String nombreResponsable;

    private String telefonoResponsbale;


    private String barrio;


    private String parroquia;



    private String canton;


    private String provincia;

    private Long idComputador;

    private Long numero;

    private String ram;

    private String discoDuro;

    private String procesador;

    private Boolean estado;

    private Boolean estadoPrestamo;

    private String observacionesComputador;

}
