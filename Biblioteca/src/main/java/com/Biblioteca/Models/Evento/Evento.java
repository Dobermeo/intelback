package com.Biblioteca.Models.Evento;


import com.Biblioteca.Models.Persona.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "eventos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String observaciones;
    private String actividades;

    private Long numParticipantes;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Long mes, anio;

    @Column(length = 10485760)
    private String documento;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
}
