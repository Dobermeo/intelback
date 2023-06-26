package com.Biblioteca.Models.Persona;



import com.Biblioteca.Models.Evento.Evento;
import com.Biblioteca.Models.Juegos.Puntuacion;
import com.Biblioteca.Models.Roles.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="persona_id", referencedColumnName = "id")
    private Persona persona;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rol_id", referencedColumnName = "id")
    private Roles roles;


    @OneToMany(targetEntity = Evento.class,mappedBy = "usuario")
    private List<Evento> evento;

    @OneToMany(targetEntity = Puntuacion.class,mappedBy = "usuario")
    private List<Puntuacion> puntuacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="aula_id", referencedColumnName = "id")
    private Aula aula;

}
