package com.Biblioteca.Repository.Ubicacion;

import com.Biblioteca.Models.Ubicacion.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbicacionRepository extends JpaRepository<Ubicacion,Long> {

    Optional<Ubicacion> findByBarrioAndCantonAndParroquiaAndProvincia(Barrio barrio, Canton canton, Parroquia parroquia, Provincia provincia);
}
