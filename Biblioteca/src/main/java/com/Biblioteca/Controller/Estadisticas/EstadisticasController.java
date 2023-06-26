package com.Biblioteca.Controller.Estadisticas;


import com.Biblioteca.DTO.Estadisticas.EstadisticasGenero;
import com.Biblioteca.Repository.Libros.ListaLibrosPrestamo;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import com.Biblioteca.Service.Computo.ComputoClienteService;
import com.Biblioteca.Service.Copias.CopiasService;
import com.Biblioteca.Service.Libros.LibrosClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired
    private LibrosClientesService librosClientesService;

    @Autowired
    private CopiasService copiasService;

    @Autowired
    private ComputoClienteService computoClienteService;

    @GetMapping("/filtrarByGeneroLibros/{mes}/{anio}")
    public ResponseEntity<EstadisticasGenero> filtrarGeneroByMesAndAnioLibros(@PathVariable Long mes, @PathVariable Long  anio){
        EstadisticasGenero filtro = librosClientesService.estadisticasGeneroLibros(mes, anio);
        return new ResponseEntity<>(filtro, HttpStatus.OK);
    }

    @GetMapping("/datosEstadisticosLibros/{mes}/{anio}")
    public ResponseEntity<List<DatosEstadicticasMesAnio>> listAllLClientesMesAndAnioLibros(@PathVariable Long mes, @PathVariable Long  anio){
        List<DatosEstadicticasMesAnio> lista = librosClientesService.listaClientesLibroMesANio(mes, anio);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/filtrarByGeneroCopias/{mes}/{anio}")
    public ResponseEntity<EstadisticasGenero> filtrarGeneroByMesAndAnioCopias(@PathVariable Long mes, @PathVariable Long  anio){
        EstadisticasGenero filtro = copiasService.estadisticasGeneroCopias(mes, anio);
        return new ResponseEntity<>(filtro, HttpStatus.OK);
    }

    @GetMapping("/datosEstadisticosCopias/{mes}/{anio}")
    public ResponseEntity<List<DatosEstadicticasMesAnio>> listAllLClientesMesAndAnioCopias(@PathVariable Long mes, @PathVariable Long  anio){
        List<DatosEstadicticasMesAnio> lista = copiasService.listaClientesLibroMesANio(mes, anio);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/filtrarByGeneroComputo/{mes}/{anio}")
    public ResponseEntity<EstadisticasGenero> filtrarGeneroByMesAndAnioComputo(@PathVariable Long mes, @PathVariable Long  anio){
        EstadisticasGenero filtro = computoClienteService.estadisticasGeneroComputo(mes, anio);
        return new ResponseEntity<>(filtro, HttpStatus.OK);
    }

    @GetMapping("/datosEstadisticosComputo/{mes}/{anio}")
    public ResponseEntity<List<DatosEstadicticasMesAnio>> listAllLClientesMesAndAnioComputo(@PathVariable Long mes, @PathVariable Long  anio){
        List<DatosEstadicticasMesAnio> lista = computoClienteService.listaClientesLibroMesANio(mes, anio);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
