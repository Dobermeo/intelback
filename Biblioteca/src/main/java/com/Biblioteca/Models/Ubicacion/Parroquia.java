package com.Biblioteca.Models.Ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "parroquia")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parroquia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parroquia;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_canton",referencedColumnName = "id")
    private Canton canton;

    @OneToMany(targetEntity = Ubicacion.class,mappedBy = "parroquia")
    private List<Ubicacion> ubicacion;

}
