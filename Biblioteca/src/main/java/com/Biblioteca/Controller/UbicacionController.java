package com.Biblioteca.Controller;


import com.Biblioteca.DTO.Ubicacion.CantonResponse;
import com.Biblioteca.DTO.Ubicacion.ParroquiaResponse;
import com.Biblioteca.DTO.Ubicacion.ProvinciaResponse;
import com.Biblioteca.Service.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @GetMapping("canton/allCantones")
    public ResponseEntity<List<CantonResponse>> listAllCantones() {
        List<CantonResponse> result = ubicacionService.listAllCantones();
        return new ResponseEntity<List<CantonResponse>>(result, HttpStatus.OK);
    }

    @GetMapping("parroquia/allParroquias")
    public ResponseEntity<List<ParroquiaResponse>> listAllParroquias() {
        List<ParroquiaResponse> result = ubicacionService.listAllParroquias();
        return new ResponseEntity<List<ParroquiaResponse>>(result, HttpStatus.OK);
    }

    @GetMapping("provincia/allProvincias")
    public ResponseEntity<List<
            ProvinciaResponse>> listAllProvincias() {
        List<ProvinciaResponse> result = ubicacionService.listAllProvincias();
        return new ResponseEntity<List<ProvinciaResponse>>(result, HttpStatus.OK);
    }
}
