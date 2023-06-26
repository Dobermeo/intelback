package com.Biblioteca.Repository.Puntuacion;

import com.Biblioteca.Models.Juegos.Juego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JuegoRepository extends JpaRepository<Juego, Long> {
}
