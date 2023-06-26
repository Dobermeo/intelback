package com.Biblioteca.Repository.Libros;

public interface ListaLibrosPrestamo {

    String getNombres();

    String getApellidos();

    String getCedula();

    String getTelefono();

    Long getIdPrestamo();

    Long getidLIbro();

    Long getDiaPrestamo();

    Long getMesPrestamo();

    Long getAnioPrestamo();

    Long getDiaDev();

    Long getMesDev();

    Long getAnioDev();

    String getCodigoLibro();

    Boolean getEstado();

    String getObservacionEntrega();

}
