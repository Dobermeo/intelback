package com.Biblioteca.Repository.Persona;

import com.Biblioteca.Models.Persona.Persona;
import com.Biblioteca.Models.Persona.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByPersona(Persona persona);

    @Override
    Optional<Usuario> findById(Long aLong);
}
