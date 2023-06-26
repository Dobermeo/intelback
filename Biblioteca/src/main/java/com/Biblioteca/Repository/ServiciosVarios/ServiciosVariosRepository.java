package com.Biblioteca.Repository.ServiciosVarios;

import com.Biblioteca.Models.Servicio.ServiciosVarios.ServiciosVarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiciosVariosRepository extends JpaRepository<ServiciosVarios, Long> {

    Optional<ServiciosVarios> findByDescripcionLikeIgnoreCase(String descripcion);
}
