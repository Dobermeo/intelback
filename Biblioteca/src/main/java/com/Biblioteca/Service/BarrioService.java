package com.Biblioteca.Service;

import com.Biblioteca.DTO.Ubicacion.BarrioRequest;
import com.Biblioteca.DTO.Ubicacion.BarrioResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Ubicacion.Barrio;
import com.Biblioteca.Repository.Ubicacion.BarrioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BarrioService {

    @Autowired
    private BarrioRepository barrioRepository;

    public boolean regitrarBarrio (BarrioRequest barrioRequest){
        Optional<Barrio> optionalBarrio = barrioRepository.findByBarrioLikeIgnoreCase(barrioRequest.getBarrio());
        if(!optionalBarrio.isPresent()) {
            Barrio newBarrio = new Barrio();
            newBarrio.setBarrio(barrioRequest.getBarrio());
            try{
                barrioRepository.save(newBarrio);
                return true;
            }catch (Exception e){
                throw new BadRequestException("No se registró el barrio" +e);
            }
        }else{
            throw new BadRequestException("Ya existe un barrio con ese nombre");
        }
    }

    public BarrioResponse barrioById(Long id){
        Optional<Barrio> barrio = barrioRepository.findById(id);
        if(barrio.isPresent()) {
            BarrioResponse response = new BarrioResponse();
            response.setBarrio(barrio.get().getBarrio());
            return response;
        }else{
            throw new BadRequestException("No existe un barrio con id" +id);
        }
    }



    //LISTAR TODOS LOS BARRIOS
    public List<BarrioResponse> listAllBarrios() {
        List<Barrio> barrio = barrioRepository.findAll();
        return barrio.stream().map(barrioRequest->{
            BarrioResponse response = new BarrioResponse();
            response.setId(barrioRequest.getId());
            response.setBarrio(barrioRequest.getBarrio());
            return response;
        }).collect(Collectors.toList());
    }
}
