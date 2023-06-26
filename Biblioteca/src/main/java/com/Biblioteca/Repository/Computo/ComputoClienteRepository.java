package com.Biblioteca.Repository.Computo;

import com.Biblioteca.DTO.Computo.ComputoClienteResponse;
import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComputoClienteRepository extends JpaRepository<ComputoCliente, Long> {

    @Query(value = "select distinct count(*)\n" +
            "from persona p, cliente c, inventario_computo l, computo_cliente pl\n" +
            "where c.genero = :genero and pl.mes = :mes and pl.anio  = :anio \n" +
            "and c.persona_id = p.id and pl.id_cliente = c.id and pl.id_inventario  = l.id ", nativeQuery = true)
    Long countDistinctByGeneroAndMesPrestamoAndAnioPrestamo(String genero,Long mes, Long anio);

    List<ComputoCliente> findAllByHoraFin(String horaFin);


    @Query(value = "select p.nombres as nombres, p.apellidos as apellidos, c.genero as genero\n" +
            "from persona p, cliente c, inventario_computo l, computo_cliente pl\n" +
            "where pl.mes = :mes and pl.anio  = :anio \n" +
            "and c.persona_id = p.id and pl.id_cliente = c.id and pl.id_inventario  = l.id ", nativeQuery = true)
    List<DatosEstadicticasMesAnio> findAllByMesandAnio(Long mes, Long anio);
}
