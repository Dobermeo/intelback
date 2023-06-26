package com.Biblioteca.Models.Servicio.CentroComputo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "inventario_computo")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventarioComputo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numero;

    private String ram;

    private String discoDuro;

    private String procesador;

    private Boolean estado;

    private Boolean estadoPrestamo;

    private String observacionesComputador;

    @OneToMany (targetEntity = ComputoCliente.class, mappedBy = "inventario")
    private List<ComputoCliente> computoCliente;
}
