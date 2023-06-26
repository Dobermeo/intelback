package com.Biblioteca.Models.CursoTaller.Taller;


import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Models.Persona.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "taller")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Taller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cursoTaller_id", referencedColumnName = "id")
    private CursoTaller cursoTaller;

    @ManyToMany(mappedBy ="talleres")
    private List<Cliente> clientes = new ArrayList<>();
}
