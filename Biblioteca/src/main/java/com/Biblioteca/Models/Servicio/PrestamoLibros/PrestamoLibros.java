package com.Biblioteca.Models.Servicio.PrestamoLibros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "prestamo_libros")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoLibros implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoLibro;

    private String nombre;

    private  String autor;

    private String isbn;

    private Boolean estado;


    @OneToMany (targetEntity = PrestamoLibroCliente.class, mappedBy = "prestamo")
    private List<PrestamoLibroCliente> prestamoCliente;

}
