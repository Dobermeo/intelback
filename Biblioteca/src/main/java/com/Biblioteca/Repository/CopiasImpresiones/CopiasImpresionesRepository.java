package com.Biblioteca.Repository.CopiasImpresiones;

import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasImpresiones;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CopiasImpresionesRepository extends JpaRepository<CopiasImpresiones, Long> {

    @Query(value = "select distinct count(*)\n" +
            "from persona p, cliente c, copias_impresiones l, copias_cliente pl\n" +
            "where  c.genero = :genero and pl.mes = :mes and pl.anio  = :anio \n" +
            "and c.persona_id = p.id and pl.id_cliente = c.id and pl.id_copias = l.id ", nativeQuery = true)
    Long countDistinctByGeneroAndMesAndAnio(String genero,Long mes, Long anio);



}
