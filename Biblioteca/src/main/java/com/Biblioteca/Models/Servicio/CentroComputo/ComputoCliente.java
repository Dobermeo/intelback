package com.Biblioteca.Models.Servicio.CentroComputo;

import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibros;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "computo_cliente")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComputoCliente implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String horaInicio;

        private String horaFin;

        private Long dia;

        private Long mes;

        private Long anio;

        @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        @JoinColumn(name = "id_cliente",referencedColumnName = "id")
        private Cliente cliente;

        private String descripcion;

        @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        @JoinColumn(name = "id_inventario",referencedColumnName = "id")
        private InventarioComputo inventario;

}
