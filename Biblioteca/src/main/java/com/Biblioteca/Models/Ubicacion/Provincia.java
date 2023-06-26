package com.Biblioteca.Models.Ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "provincia")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Provincia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provincia;

    @OneToMany (targetEntity = Canton.class, mappedBy = "provincia")
    private List<Canton> canton;

    @OneToMany(targetEntity = Ubicacion.class,mappedBy = "provincia")
    private List<Ubicacion> ubicacion;
}
