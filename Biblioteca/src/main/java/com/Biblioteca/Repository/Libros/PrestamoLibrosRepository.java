package com.Biblioteca.Repository.Libros;

import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrestamoLibrosRepository extends JpaRepository<PrestamoLibros, Long> {

    Optional<PrestamoLibros> findByCodigoLibroLikeIgnoreCase(String codigo);

    List<PrestamoLibros> findAllByEstado(Boolean estado);
}
