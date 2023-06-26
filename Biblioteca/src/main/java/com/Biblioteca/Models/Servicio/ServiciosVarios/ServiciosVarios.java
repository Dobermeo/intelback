package com.Biblioteca.Models.Servicio.ServiciosVarios;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "servicios_varios")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiciosVarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @OneToMany (targetEntity = ServiciosVariosCliente.class, mappedBy = "serviciosVarios")
    private List<ServiciosVariosCliente> serviciosVariosCliente;
}
