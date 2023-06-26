package com.Biblioteca.Controller.PrestamoLibros;


import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.LibrosClientesRequest;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Repository.Libros.ListaLibrosPrestamo;
import com.Biblioteca.Service.Libros.LibrosClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/libro/cliente")
public class ClienteLibroController {

    @Autowired
    private LibrosClientesService librosClientesService;

    @PostMapping("/registroprestamos")
    public ResponseEntity<?> create(@RequestBody LibrosClientesRequest request){
        librosClientesService.registroPrestamos(request);
        return new ResponseEntity(new Mensaje("Libro-Cliente Creado"), HttpStatus.CREATED);
    }


    @PutMapping("/actualizarregistroprestamos")
    public ResponseEntity<?> actualizar(@RequestBody LibrosClientesRequest request){
        return new ResponseEntity<>(librosClientesService.updatePrestamo(request), HttpStatus.OK);
    }

    @GetMapping("/lista/librosenprestamo")
         public ResponseEntity<List<ListaLibrosPrestamo>> listAllLIbrosEnPrestamo(){
        List<ListaLibrosPrestamo> lista = librosClientesService.listaLbrosEnPrestamo();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
