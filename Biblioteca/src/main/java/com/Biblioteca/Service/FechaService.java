package com.Biblioteca.Service;

import com.Biblioteca.DTO.fecha.FechaResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class FechaService {

    public FechaResponse obtenerFechaactual(){
        FechaResponse f= new FechaResponse();
        f.setFechaactual(LocalDateTime.now());
        return f;
    }
}
