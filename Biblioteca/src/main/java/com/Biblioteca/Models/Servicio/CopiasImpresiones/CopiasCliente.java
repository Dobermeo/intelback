package com.Biblioteca.Models.Servicio.CopiasImpresiones;

import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibros;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "copias_cliente")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CopiasCliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_actual")
    @Temporal(TemporalType.DATE)
    private Date fecha_actual;


    private Long dia, mes, anio;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_cliente",referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_copias",referencedColumnName = "id")
    private CopiasImpresiones copias;
}
