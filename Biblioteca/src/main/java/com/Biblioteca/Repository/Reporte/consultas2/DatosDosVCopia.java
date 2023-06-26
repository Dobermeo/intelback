package com.Biblioteca.Repository.Reporte.consultas2;

import com.Biblioteca.Models.Servicio.ServiciosVarios.ServiciosVariosCliente;
import com.Biblioteca.Repository.Reporte.DatosReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatosDosVCopia extends JpaRepository<ServiciosVariosCliente, Long> {

@Query(value = "select p.cedula,p.nombres, p.apellidos, p.telefono, p.email, " +
        "cl.genero,cl.estado_civil,cl.fecha_nacimiento,cl.edad,cl.discapacidad, " +
        "svc.dia_prestamo,svc.mes_prestamo, svc.anio_prestamo, " +
        "pro.provincia, cant.canton, barr.barrio, parr.parroquia " +
        "from persona p,ubicacion ub,provincia pro,canton cant,barrio barr,parroquia parr, cliente cl, servicios_varios sv, servicios_varios_cliente svc " +
        "where svc.mes_prestamo = :mes and svc.anio_prestamo  = :anio " +
        "and cl.persona_id = p.id and cl.id_ubicacion=ub.id and ub.provincia_id=pro.id and ub.canton_id=cant.id and ub.parroquia_id=parr.id and ub.barrio_id=barr.id " +
        "and svc.id_cliente = cl.id and svc.id_servicio_varios  = sv.id " +
        "and svc.id_cliente NOT IN (SELECT id_cliente FROM computo_cliente where mes=:mes and anio=:anio and dia=svc.dia_prestamo) " +
        "and svc.id_cliente IN (SELECT id_cliente FROM copias_cliente where mes=:mes and anio= :anio and dia=svc.dia_prestamo)" +
        "and svc.id_cliente NOT IN (SELECT id_cliente FROM prestamolibros_cliente where mes_prestamo= :mes and anio_prestamo=:anio and dia_prestamo=svc.dia_prestamo)", nativeQuery = true)
List<DatosReporte> findAllByMesandAnio(Long mes, Long anio);
}