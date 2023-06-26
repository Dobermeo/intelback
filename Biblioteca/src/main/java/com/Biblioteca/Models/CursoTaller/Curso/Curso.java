package com.Biblioteca.Models.CursoTaller.Curso;

import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Table(name = "curso")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materiales;

    private Long numParticipantes;

    private String actividades;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cursoTaller_id", referencedColumnName = "id")
    private CursoTaller cursoTaller;

    @ManyToMany(mappedBy ="cursos")
    private List<Cliente> clientes = new ArrayList<>();

}
