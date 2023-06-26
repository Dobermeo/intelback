package com.Biblioteca.Models.Servicio.ServiciosVarios;

import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibros;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "servicios_varios_cliente")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiciosVariosCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String observaciones;

    private Long diaPrestamo;

    private Long mesPrestamo;

    private Long anioPrestamo;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_cliente",referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_servicioVarios",referencedColumnName = "id")
    private ServiciosVarios serviciosVarios;
}
