package com.Biblioteca.Models.Persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "persona")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cedula;

    private String apellidos;

    private String nombres;

    private String email;

    private String telefono;

    @OneToOne(mappedBy = "persona")
    private Cliente clientes;

    @OneToOne(mappedBy = "persona")
    private Usuario usuarios;
}
