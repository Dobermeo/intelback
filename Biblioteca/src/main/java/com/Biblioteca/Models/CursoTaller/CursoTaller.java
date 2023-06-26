package com.Biblioteca.Models.CursoTaller;

import com.Biblioteca.Models.CursoTaller.Curso.Curso;
import com.Biblioteca.Models.CursoTaller.Taller.Taller;
import com.Biblioteca.Models.Persona.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "curso_taller")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoTaller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String lugar;

    private String descripcion;

//    private String observaciones;

    private String responsable;





    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "fecha_maxinscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaMaxInscripcion;

    @OneToOne(mappedBy = "cursoTaller")
    private Curso cursos;

    @OneToOne(mappedBy = "cursoTaller")
    private Taller talleres;



}
