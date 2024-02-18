package com.Biblioteca.Controller.Puntuacion;

import com.Biblioteca.DTO.Puntuacion.PuntuacionCompleta;
import com.Biblioteca.DTO.Puntuacion.PuntuacionRequest;
import com.Biblioteca.DTO.Puntuacion.PuntuacionResponse;
import com.Biblioteca.Models.Juegos.Puntuacion;
import com.Biblioteca.Repository.Puntuacion.PuntuacionRepository;
import com.Biblioteca.Service.PuntuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://31.220.83.117"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/puntuacion")
public class PuntuacionController {

    @Autowired
    private PuntuacionService puntuacionService;

    @Autowired
    private PuntuacionRepository puntuacionRepository;

    @PostMapping("/registroPuntuacion")
    public ResponseEntity<PuntuacionResponse> registroPuntuacion(@RequestBody PuntuacionRequest puntuacionRequest) throws Exception {
        PuntuacionResponse puntuacionResponse=puntuacionService.registrarPuntuacion(puntuacionRequest);
        if(puntuacionResponse==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(puntuacionResponse, HttpStatus.CREATED);
        }
    }

    @GetMapping("/todasPuntuaciones")
    public ResponseEntity<List<PuntuacionCompleta>> todasLasPuntuaciones() {
        List<Puntuacion> puntuaciones = puntuacionService.obtenerTodasLasPuntuaciones();
        List<PuntuacionCompleta> puntuacionesCompletas = puntuaciones.stream().map(puntuacion -> {
            PuntuacionResponse puntuacionResponse = new PuntuacionResponse(puntuacion.getId(), puntuacion.getPuntuacion(), puntuacion.getIntento(), puntuacion.getJuego().getId(), puntuacion.getUsuario().getId());
            return puntuacionService.obtDatosCompletos(puntuacionResponse);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(puntuacionesCompletas, HttpStatus.OK);
    }





}
