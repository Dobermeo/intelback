package com.Biblioteca.Models.Persona;


import com.Biblioteca.Models.CursoTaller.Curso.Curso;
import com.Biblioteca.Models.CursoTaller.Taller.Taller;
import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasCliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import com.Biblioteca.Models.Servicio.ServiciosVarios.ServiciosVariosCliente;
import com.Biblioteca.Models.Ubicacion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "cliente")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    private Long edad;

    private String estadoCivil;

    private String genero;

    private Boolean discapacidad;

    private String nombreResponsable;

    private String telefonoResponsbale;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="persona_id", referencedColumnName = "id")
    private Persona persona;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_ubicacion",referencedColumnName = "id")
    private Ubicacion ubicacion;



    @OneToMany (targetEntity = PrestamoLibroCliente.class, mappedBy = "cliente")
    private List<PrestamoLibroCliente> prestamoCliente;

    @OneToMany (targetEntity = ServiciosVariosCliente.class, mappedBy = "cliente")
    private List<ServiciosVariosCliente> servicioVariosCliente;

    @OneToMany (targetEntity = ComputoCliente.class, mappedBy = "cliente")
    private List<ComputoCliente> computoCliente;

    @OneToMany (targetEntity = CopiasCliente.class, mappedBy = "cliente")
    private List<CopiasCliente> copiasCliente;



    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "cursos_clientes",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos = new ArrayList<>();


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "taller_clientes",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "taller_id")
    )
    private List<Taller> talleres = new ArrayList<>();
}
