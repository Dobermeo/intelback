package com.Biblioteca.Models.Servicio.CopiasImpresiones;


import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "copias_impresiones")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CopiasImpresiones implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pagBlanco;

    private Long pagColor;

    private Long pagTotal;

    @OneToMany (targetEntity = CopiasCliente.class, mappedBy = "copias")
    private List<CopiasCliente> copiasCliente;


}
