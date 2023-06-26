package com.Biblioteca.Repository.Reporte;

import com.Biblioteca.Models.CursoTaller.Taller.Taller;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReporteporTallerRepository extends JpaRepository<Taller, Long> {

    @Query(value = "SELECT ct.fecha_fin, ct.fecha_inicio, ct.nombre, p.nombres,p.cedula,p.apellidos, cli.genero, p.telefono, p.email,cli.estado_civil,cli.fecha_nacimiento,cli.edad,cli.discapacidad, pro.provincia, cant.canton, barr.barrio, parr.parroquia " +
            "from curso_taller ct, taller t, persona p,ubicacion ub,provincia pro,canton cant,barrio barr,parroquia parr,  cliente cli, taller_clientes tacli " +
            "where ct.id=t.curso_taller_id and tacli.taller_id=t.id and tacli.cliente_id=cli.id and cli.persona_id = p.id and cli.id_ubicacion=ub.id and ub.provincia_id=pro.id and ub.canton_id=cant.id and ub.parroquia_id=parr.id and ub.barrio_id=barr.id " +
            "and CAST(ct.fecha_inicio AS text) LIKE CONCAT(:fecha_inicio,'%') ", nativeQuery = true)
    List<DatosReporte> findAllByFecha_inicio(String fecha_inicio);


}
