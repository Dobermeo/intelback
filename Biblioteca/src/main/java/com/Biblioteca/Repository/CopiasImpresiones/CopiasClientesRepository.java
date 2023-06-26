package com.Biblioteca.Repository.CopiasImpresiones;

import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasCliente;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CopiasClientesRepository extends JpaRepository<CopiasCliente, Long> {
    List<CopiasCliente> findByMesAndAnio(Long mes, Long anio);

    @Query(value = "select p.nombres as nombres, p.apellidos as apellidos, c.genero as genero\n" +
            "from persona p, cliente c, copias_impresiones l, copias_cliente pl\n" +
            "where pl.mes = :mes and pl.anio  = :anio \n" +
            "and c.persona_id = p.id and pl.id_cliente = c.id and pl.id_copias = l.id ", nativeQuery = true)
    List<DatosEstadicticasMesAnio> findAllByMesandAnio(Long mes, Long anio);
}
