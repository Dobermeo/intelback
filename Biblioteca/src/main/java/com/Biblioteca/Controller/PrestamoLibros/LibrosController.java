package com.Biblioteca.Controller.PrestamoLibros;


import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.PrestamoLibrosEstadoRequest;
import com.Biblioteca.DTO.Servicios.PrestamoLibros.PrestamoLibrosRequest;
import com.Biblioteca.DTO.Servicios.PrestamoLibros.PrestamoLibrosResponse;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Service.Libros.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/libros")
public class LibrosController {

    @Autowired
    private LibrosService librosService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PrestamoLibrosRequest request){
        librosService.registroLibros(request);
        return new ResponseEntity(new Mensaje("Libro Creado"), HttpStatus.CREATED);
    }


    @PutMapping("/estado/libro")
    public ResponseEntity<?> update(@RequestBody PrestamoLibrosEstadoRequest request){
        librosService.updateLibro(request);
        return new ResponseEntity(new Mensaje("Estado de Libro Actualizado"), HttpStatus.OK);
    }

    @PutMapping("/libro")
    public ResponseEntity<?> updateAllAtributos(@RequestBody PrestamoLibrosRequest request){
        librosService.updateLibroAllAtributos(request);
        return new ResponseEntity(new Mensaje("Libro Actualizado"), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<PrestamoLibrosResponse>>listLibros(){
        List<PrestamoLibrosResponse> libros =librosService.listAllLibros();
        return new ResponseEntity<List<PrestamoLibrosResponse>>(libros,HttpStatus.OK);
    }

    @GetMapping("/all/estado")
    public ResponseEntity<List<PrestamoLibrosResponse>>listLibrosEstadoFalse(){
        List<PrestamoLibrosResponse> libros =librosService.listAllLibrosByEstado();
        return new ResponseEntity<List<PrestamoLibrosResponse>>(libros,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoLibrosResponse> listLibroId(@PathVariable Long id) {
        PrestamoLibrosResponse ser = librosService.listAllLibrosById(id);
        return new ResponseEntity<>(ser, HttpStatus.OK);

    }

    @GetMapping("/byCodigo/{codigo}")
    public ResponseEntity<PrestamoLibrosResponse> listLibroByCodigo(@PathVariable String codigo) {
        PrestamoLibrosResponse ser = librosService.listLibrosByCodigo(codigo);
        return new ResponseEntity<>(ser, HttpStatus.OK);
    }
}
