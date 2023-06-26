package com.Biblioteca.Repository.Reporte.consultas1;

import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Repository.Reporte.DatosReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatosSoloComputoRepository extends JpaRepository<ComputoCliente, Long> {

    @Query(value = "select p.cedula,p.nombres, p.apellidos, p.telefono, p.email, " +
            "cl.genero,cl.estado_civil,cl.fecha_nacimiento,cl.edad,cl.discapacidad, " +
            "com.dia,com.mes, com.anio, " +
            "pro.provincia, cant.canton, barr.barrio, parr.parroquia " +
            "from persona p,ubicacion ub,provincia pro,canton cant,barrio barr,parroquia parr, cliente cl, inventario_computo iv, computo_cliente com " +
            "where com.mes = :mes and com.anio  = :anio " +
            "and cl.persona_id = p.id and cl.id_ubicacion=ub.id and ub.provincia_id=pro.id and ub.canton_id=cant.id and ub.parroquia_id=parr.id and ub.barrio_id=barr.id " +
            "and com.id_cliente = cl.id and com.id_inventario  = iv.id " +
            "and com.id_cliente NOT IN (SELECT id_cliente FROM copias_cliente where mes= :mes and anio= :anio and dia=com.dia) " +
            "and com.id_cliente NOT IN (SELECT id_cliente FROM prestamolibros_cliente where mes_prestamo= :mes and anio_prestamo=:anio and dia_prestamo=com.dia) " +
            "and com.id_cliente NOT IN (SELECT id_cliente FROM servicios_varios_cliente where mes_prestamo= :mes and anio_prestamo=:anio and dia_prestamo=com.dia)", nativeQuery = true)

    List<DatosReporte> findAllByMesandAnio(Long mes, Long anio);

}
