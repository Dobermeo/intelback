package com.Biblioteca.Repository.Reporte;

import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportecomputoRepository extends JpaRepository<ComputoCliente, Long> {




    @Query(value = "select p.cedula as cedula,p.nombres as nombres, p.apellidos as apellidos, cl.genero as genero, p.telefono as telefono, p.email as email,cl.estado_civil,cl.fecha_nacimiento,cl.edad,cl.discapacidad," +
            " cocli.dia,cocli.mes, cocli.anio, pro.provincia, cant.canton, barr.barrio, parr.parroquia\n" +
            "from persona p,ubicacion ub,provincia pro,canton cant,barrio barr,parroquia parr, cliente cl, inventario_computo ci, computo_cliente cocli\n" +
            "where cocli.mes = :mes and cocli.anio  = :anio \n" +
            "and cl.persona_id = p.id and cl.id_ubicacion=ub.id and ub.provincia_id=pro.id and ub.canton_id=cant.id and ub.parroquia_id=parr.id and ub.barrio_id=barr.id" +
            " and cocli.id_cliente = cl.id and cocli.id_inventario  = ci.id ", nativeQuery = true)
    List<DatosReporte> findAllByMesandAnio(Long mes, Long anio);

}