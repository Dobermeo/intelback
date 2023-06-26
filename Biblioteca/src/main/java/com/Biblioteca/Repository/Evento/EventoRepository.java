package com.Biblioteca.Repository.Evento;

import com.Biblioteca.Models.Evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByMesAndAnio(Long mes, Long anio);
}
