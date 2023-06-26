package com.Biblioteca.Models.Persona;

import com.Biblioteca.Models.Juegos.Puntuacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "aula")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numeroAula;

    private String paraleloAula;

    @OneToMany(targetEntity = Usuario.class,mappedBy = "aula")
    private List<Usuario> usuario;


}
