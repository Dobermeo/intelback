package com.Biblioteca.Repository.CursoTaller;

import com.Biblioteca.Models.CursoTaller.Curso.Curso;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CursoTallerRepository extends JpaRepository<CursoTaller, Long> {


    Optional<CursoTaller> findByNombre(String nombre);


    Boolean existsByNombre(String nombre);
    @Query(value = "SELECT * FROM curso_taller ct join curso cu on cu.curso_taller_id= ct.id where ct.nombre =:nombre and ct.fecha_inicio=:fechaInicio", nativeQuery = true)
    Optional<CursoTaller> findByNombreAndFechaInicio(String nombre, Date fechaInicio);

    @Query(value = "SELECT * FROM curso_taller ct join taller ta on ta.curso_taller_id= ct.id  where ct.nombre =:nombre and ct.fecha_inicio=:fechaInicio", nativeQuery = true)
    Optional<CursoTaller> findDistinctByNombreAndFechaInicio(String nombre, Date fechaInicio);
}