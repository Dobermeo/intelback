package com.Biblioteca.Controller;

import com.Biblioteca.Service.FechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/fecha")
public class FechaController {
    @Autowired
    private FechaService fechaService;

    @GetMapping
    public ResponseEntity<?> fecha(){
        return new ResponseEntity(fechaService.obtenerFechaactual(), HttpStatus.OK);
    }
}
