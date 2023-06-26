package com.Biblioteca.Models.Juegos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "juego")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombrejuego;

    private Long descripcion;

    @OneToMany(mappedBy = "juego")
    private List<Puntuacion> puntuacion;

}
