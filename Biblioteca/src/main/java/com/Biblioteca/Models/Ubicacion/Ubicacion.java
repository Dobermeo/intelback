package com.Biblioteca.Models.Ubicacion;

import com.Biblioteca.Models.Persona.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "ubicacion")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "barrio_id", referencedColumnName = "id")
    private Barrio barrio;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="canton_id", referencedColumnName = "id")
    private Canton canton;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="parroquia_id", referencedColumnName = "id")
    private Parroquia parroquia;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="provincia_id", referencedColumnName = "id")
    private Provincia provincia;

    @OneToMany (targetEntity = Cliente.class, mappedBy = "ubicacion")
    private  List<Cliente> clientes;
}
