package com.Biblioteca.Repository.Computo;

import com.Biblioteca.Models.Servicio.CentroComputo.InventarioComputo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventarioComputoRepository extends JpaRepository<InventarioComputo, Long> {

    Optional<InventarioComputo> findByNumero(Long numero);

    @Query(value = "SELECT *\n" +
            "FROM inventario_computo\n" +
            "where estado = 'false' and estado_prestamo = 'false'\n" +
            "ORDER BY numero ASC ", nativeQuery = true)
    List<InventarioComputo> findAllByEstado(Boolean estado);
}
