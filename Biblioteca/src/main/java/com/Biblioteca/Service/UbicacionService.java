package com.Biblioteca.Service;


import com.Biblioteca.DTO.Ubicacion.CantonResponse;
import com.Biblioteca.DTO.Ubicacion.ParroquiaResponse;
import com.Biblioteca.DTO.Ubicacion.ProvinciaResponse;
import com.Biblioteca.Models.Ubicacion.Canton;
import com.Biblioteca.Models.Ubicacion.Parroquia;
import com.Biblioteca.Models.Ubicacion.Provincia;
import com.Biblioteca.Repository.Ubicacion.CantonRepository;
import com.Biblioteca.Repository.Ubicacion.ParroquiaRepository;
import com.Biblioteca.Repository.Ubicacion.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UbicacionService {

    @Autowired
    private CantonRepository cantonRepository;

    @Autowired
    private ParroquiaRepository parroquiaRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    //LISTAR TODOS LOS CANTONES

    public List<CantonResponse> listAllCantones(){
        List<Canton> canton = cantonRepository.findAll();
        return canton.stream().map(cantonRequest ->{
            CantonResponse cantonResponse = new CantonResponse();
            cantonResponse.setId(cantonRequest.getId());
            cantonResponse.setCanton(cantonRequest.getCanton());
            cantonResponse.setIdProvincia(cantonRequest.getProvincia().getId());
            return cantonResponse;
        }).collect(Collectors.toList());
    }

    //LISTAR TODAS LAS PARROQUIAS

    public List<ParroquiaResponse> listAllParroquias(){
        List<Parroquia> parroquia = parroquiaRepository.findAll();
        return parroquia.stream().map(parroquiaRequest ->{
            ParroquiaResponse parroquiaResponse = new ParroquiaResponse();
            parroquiaResponse.setId(parroquiaRequest.getId());
            parroquiaResponse.setParroquia(parroquiaRequest.getParroquia());
            parroquiaResponse.setIdCanton(parroquiaRequest.getCanton().getId());
            return parroquiaResponse;
        }).collect(Collectors.toList());
    }


    //LISTAR TODAS LAS PROVINCIAS
    public List<ProvinciaResponse> listAllProvincias(){
        List<Provincia> provincia = provinciaRepository.findAll();
        return provincia.stream().map(provinciaRequest ->{
            ProvinciaResponse provinciaResponse = new ProvinciaResponse();
            provinciaResponse.setId(provinciaRequest.getId());
            provinciaResponse.setProvincia(provinciaRequest.getProvincia());
            return provinciaResponse;
        }).collect(Collectors.toList());
    }
}
