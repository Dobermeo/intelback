package com.Biblioteca.Repository.ServiciosVarios;

import com.Biblioteca.Models.Servicio.ServiciosVarios.ServiciosVariosCliente;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiciosVariosClienteRepository extends JpaRepository<ServiciosVariosCliente,Long> {

    @Query(value = "select svc.id as id, c.id as cliente, p.cedula as cedula, p.nombres as nombres, p.apellidos as apellidos, p.telefono as telefono, sv.descripcion as servicio , svc.observaciones as observaciones\n" +
            "from persona p, cliente c, servicios_varios sv, servicios_varios_cliente svc\n" +
            "where svc.mes_prestamo = :mes and svc.anio_prestamo = :anio \n" +
            "and c.persona_id = p.id and svc.id_cliente = c.id and svc.id_servicio_varios = sv.id ", nativeQuery = true)
    List<DatosServicioVarioCliente> findAllByMesAndAnio(Long mes, Long anio);
}
