package com.Biblioteca.Controller;


import com.Biblioteca.DTO.Ubicacion.BarrioRequest;
import com.Biblioteca.DTO.Ubicacion.BarrioResponse;
import com.Biblioteca.Service.BarrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/barrio")
public class BarrioController {

    @Autowired
    private BarrioService barrioService;

    @PostMapping("/registrarBarrio")
    public ResponseEntity<?> registroBarrio(@RequestBody BarrioRequest request){
        return new ResponseEntity<>(barrioService.regitrarBarrio(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarrioResponse> barrioById(@PathVariable Long id){
        BarrioResponse response = barrioService.barrioById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BarrioResponse>> all(){
        List<BarrioResponse> allBarrios = barrioService.listAllBarrios();
        return new ResponseEntity<>(allBarrios, HttpStatus.OK);
    }
}
