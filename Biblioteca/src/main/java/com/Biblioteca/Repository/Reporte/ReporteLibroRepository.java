package com.Biblioteca.Repository.Reporte;

import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReporteLibroRepository extends JpaRepository<PrestamoLibroCliente, Long> {


    @Query(value = "select p.cedula as cedula,p.nombres as nombres, p.apellidos as apellidos, cl.genero as genero, p.telefono as telefono, p.email as email,cl.estado_civil,cl.fecha_nacimiento,cl.edad,cl.discapacidad, " +
            " plcli.dia_prestamo,plcli.mes_prestamo,plcli.anio_prestamo,pro.provincia, cant.canton, barr.barrio, parr.parroquia\n" +
            "from persona p,ubicacion ub,provincia pro,canton cant,barrio barr,parroquia parr, cliente cl, prestamo_libros pl, prestamolibros_cliente plcli\n" +
            "where plcli.mes_prestamo = :mes and plcli.anio_prestamo  = :anio \n" +
            "and cl.persona_id = p.id and cl.id_ubicacion=ub.id and ub.provincia_id=pro.id and ub.canton_id=cant.id and ub.parroquia_id=parr.id and ub.barrio_id=barr.id" +
            " and plcli.id_cliente = cl.id and plcli.id_prestamo  = pl.id ", nativeQuery = true)
    List<DatosReporte> findAllByMesandAnio(Long mes, Long anio);
}