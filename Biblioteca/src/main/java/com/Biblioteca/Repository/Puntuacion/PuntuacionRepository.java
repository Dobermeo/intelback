package com.Biblioteca.Repository.Puntuacion;


import com.Biblioteca.Models.Juegos.Juego;
import com.Biblioteca.Models.Juegos.Puntuacion;
import com.Biblioteca.Models.Persona.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {

    boolean existsById(Long id);

    Optional<Puntuacion> findByJuegoAndUsuario(Juego juego, Usuario usuario);

    List<Puntuacion> findByUsuarioAndJuego(Usuario obtusuario, Juego obtjuego);
}
