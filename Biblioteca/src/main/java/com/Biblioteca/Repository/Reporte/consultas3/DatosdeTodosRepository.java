package com.Biblioteca.Repository.Reporte.consultas3;

import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasCliente;
import com.Biblioteca.Repository.Reporte.DatosReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatosdeTodosRepository  extends JpaRepository<CopiasCliente, Long> {


    @Query(value = "select p.cedula,p.nombres, p.apellidos, p.telefono, p.email," +
            " cl.genero,cl.estado_civil,cl.fecha_nacimiento,cl.edad,cl.discapacidad, " +
            "cocli.fecha_actual, cocli.dia,cocli.mes, cocli.anio, " +
            "pro.provincia, cant.canton, barr.barrio, parr.parroquia " +
            "from persona p,ubicacion ub,provincia pro,canton cant,barrio barr,parroquia parr, cliente cl, copias_impresiones ci, copias_cliente cocli " +
            "where cocli.mes = :mes and cocli.anio  = :anio " +
            "and cl.persona_id = p.id and cl.id_ubicacion=ub.id and ub.provincia_id=pro.id and ub.canton_id=cant.id and ub.parroquia_id=parr.id and ub.barrio_id=barr.id " +
            "and cocli.id_cliente = cl.id and cocli.id_copias  = ci.id " +
            "and cocli.id_cliente IN (SELECT id_cliente FROM computo_cliente where mes= :mes and anio= :anio and dia=cocli.dia) " +
            "and cocli.id_cliente IN (SELECT id_cliente FROM prestamolibros_cliente where mes_prestamo= :mes and anio_prestamo= :anio and dia_prestamo=cocli.dia) " +
            "and cocli.id_cliente IN (SELECT id_cliente FROM servicios_varios_cliente where mes_prestamo= :mes and anio_prestamo= :anio  and dia_prestamo=cocli.dia)", nativeQuery = true)
    List<DatosReporte> findAllByMesandAnio(Long mes, Long anio);
}