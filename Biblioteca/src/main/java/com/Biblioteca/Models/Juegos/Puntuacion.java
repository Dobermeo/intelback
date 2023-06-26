package com.Biblioteca.Models.Juegos;

import com.Biblioteca.Models.Persona.Usuario;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "puntuacion")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Puntuacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long puntuacion;

    private Long intento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="juego_id", referencedColumnName = "id")
    private Juego juego;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}
