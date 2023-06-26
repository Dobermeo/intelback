package com.Biblioteca.Repository.Reporte;

import java.util.Date;

public interface DatosReporte {
        String getCedula();
        String getNombres();
        String getNombre();
        String getApellidos();
        String getGenero();
        String getTelefono();
        String getEmail();
        Date getFecha_actual();
        String getFecha_inicio();
        String getFecha_fin();
        Long getDia();
        Long getAnio();
        Long getMes();
        Long getDia_prestamo();
        Long getAnio_prestamo();
        Long getMes_prestamo();
        Long getId();

        Date getFecha_nacimiento();
        Long getEdad();
        String getEstado_civil();
        String getProvincia();
        String getCanton();
        String getParroquia();
        String getBarrio();
        Boolean getDiscapacidad();
        String getverificables();



    }
