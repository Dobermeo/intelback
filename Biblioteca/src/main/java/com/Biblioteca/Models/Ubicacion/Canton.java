package com.Biblioteca.Models.Ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "canton")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Canton implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String canton;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_provincia",referencedColumnName = "id")
    private Provincia provincia;

    @OneToMany (targetEntity = Parroquia.class, mappedBy = "canton")
    private List<Parroquia> parroquia;

    @OneToMany(targetEntity = Ubicacion.class,mappedBy = "canton")
    private List<Ubicacion> ubicacion;

}
