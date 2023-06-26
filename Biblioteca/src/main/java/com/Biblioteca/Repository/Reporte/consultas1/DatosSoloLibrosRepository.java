package com.Biblioteca.Repository.Reporte.consultas1;

import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import com.Biblioteca.Repository.Reporte.DatosReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatosSoloLibrosRepository  extends JpaRepository<PrestamoLibroCliente, Long> {


    @Query(value = "select p.cedula,p.nombres, p.apellidos, p.telefono, p.email, " +
            "cl.genero,cl.estado_civil,cl.fecha_nacimiento,cl.edad,cl.discapacidad, " +
            "precli.dia_prestamo,precli.mes_prestamo, precli.anio_prestamo, " +
            "pro.provincia, cant.canton, barr.barrio, parr.parroquia " +
            "from persona p,ubicacion ub,provincia pro,canton cant,barrio barr,parroquia parr, cliente cl,prestamo_libros pre, prestamolibros_cliente precli " +
            "where precli.mes_prestamo = :mes and precli.anio_prestamo  = :anio " +
            "and cl.persona_id = p.id and cl.id_ubicacion=ub.id and ub.provincia_id=pro.id and ub.canton_id=cant.id and ub.parroquia_id=parr.id and ub.barrio_id=barr.id " +
            "and precli.id_cliente = cl.id and precli.id_prestamo = pre.id " +
            "and precli.id_cliente NOT IN (SELECT id_cliente FROM copias_cliente where mes= :mes and anio= :anio and dia=precli.dia_prestamo) " +
            "and precli.id_cliente NOT IN (SELECT id_cliente FROM computo_cliente where mes= :mes and anio= :anio and dia=precli.dia_prestamo)" +
            "and precli.id_cliente NOT IN (SELECT id_cliente FROM servicios_varios_cliente where mes_prestamo=:mes and anio_prestamo=:anio and dia_prestamo=precli.dia_prestamo)" , nativeQuery = true)
    List<DatosReporte> findAllByMesandAnio(Long mes, Long anio);
}
