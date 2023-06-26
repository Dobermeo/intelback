package com.Biblioteca.Models.Servicio.PrestamoLibros;

import com.Biblioteca.Models.Persona.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "prestamolibros_cliente")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoLibroCliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long diaPrestamo;

    private Long mesPrestamo;

    private Long anioPrestamo;

    private Long diaDev;

    private Long mesDev;

    private Long anioDev;

    private String observacionesEntrega;

    private String observacionesDev;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_cliente",referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_prestamo",referencedColumnName = "id")
    private PrestamoLibros prestamo;

}
