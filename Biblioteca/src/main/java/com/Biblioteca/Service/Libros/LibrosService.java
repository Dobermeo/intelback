package com.Biblioteca.Service.Libros;

import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.PrestamoLibrosEstadoRequest;
import com.Biblioteca.DTO.Servicios.PrestamoLibros.PrestamoLibrosRequest;
import com.Biblioteca.DTO.Servicios.PrestamoLibros.PrestamoLibrosResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibros;
import com.Biblioteca.Repository.Libros.PrestamoLibrosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LibrosService {

    @Autowired
    private PrestamoLibrosRepository prestamoLibrosRepository;

    @Transactional
    public boolean registroLibros(PrestamoLibrosRequest request){
        Optional<PrestamoLibros> optional = prestamoLibrosRepository.findByCodigoLibroLikeIgnoreCase(request.getCodigoLibro());
        if (!optional.isPresent()) {
            PrestamoLibros newLibro = new PrestamoLibros();
            newLibro.setCodigoLibro(request.getCodigoLibro());
            newLibro.setNombre(request.getNombre());
            newLibro.setAutor(request.getAutor());
            newLibro.setIsbn(request.getIsbn());
            newLibro.setEstado(request.getEstado());
            try{
                prestamoLibrosRepository.save(newLibro);
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se guardó el libro" + ex);
            }

        }else{
            throw new BadRequestException("Ya existe un libro con el mismo código");
        }
    }

    public boolean updateLibro(PrestamoLibrosEstadoRequest request) {
        Optional<PrestamoLibros> optional = prestamoLibrosRepository.findById(request.getId());
        if (optional.isPresent()) {
            optional.get().setEstado(request.getEstado());


            try {
                prestamoLibrosRepository.save(optional.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("No se actualizó el estado de libro" + ex);
            }
        } else {
            throw new BadRequestException("No existe el libro" + request.getId());

        }
    }

    public boolean updateLibroAllAtributos(PrestamoLibrosRequest request) {
        Optional<PrestamoLibros> optional = prestamoLibrosRepository.findById(request.getId());
        if (optional.isPresent()) {
            optional.get().setCodigoLibro(request.getCodigoLibro());
            optional.get().setEstado(request.getEstado());
            optional.get().setAutor(request.getAutor());
            optional.get().setNombre(request.getNombre());
            optional.get().setIsbn(request.getIsbn());

            try {
                prestamoLibrosRepository.save(optional.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("No se actualizó el estado de libro" + ex);
            }
        } else {
            throw new BadRequestException("No existe el libro" + request.getId());

        }
    }

    public List<PrestamoLibrosResponse> listAllLibros(){
        List<PrestamoLibros> lista = prestamoLibrosRepository.findAll();
        return lista.stream().map(libro->{
            PrestamoLibrosResponse response = new PrestamoLibrosResponse();
            response.setId(libro.getId());
            response.setCodigoLibro(libro.getCodigoLibro());
            response.setEstado(libro.getEstado());
            response.setAutor(libro.getAutor());
            response.setNombre(libro.getNombre());
            response.setIsbn(libro.getIsbn());
            return response;
        }).collect(Collectors.toList());
    }

    public PrestamoLibrosResponse listLibrosByCodigo(String codigo){
        PrestamoLibrosResponse response = new PrestamoLibrosResponse();
        Optional<PrestamoLibros> libro = prestamoLibrosRepository.findByCodigoLibroLikeIgnoreCase(codigo);
        if(libro.isPresent()){
            response.setId(libro.get().getId());
            response.setCodigoLibro(libro.get().getCodigoLibro());
            response.setEstado(libro.get().getEstado());
            return response;
        }else{
            throw new BadRequestException("No existe el libro" + codigo);
        }
    }

    @Transactional
    public PrestamoLibrosResponse listAllLibrosById(Long id){
        PrestamoLibrosResponse response = new PrestamoLibrosResponse();
        Optional<PrestamoLibros> libro = prestamoLibrosRepository.findById(id);
        if(libro.isPresent()){
            response.setId(libro.get().getId());
            response.setCodigoLibro(libro.get().getCodigoLibro());
            response.setEstado(libro.get().getEstado());
            return response;
        }else{
            throw new BadRequestException("No existe el libro" + id);
        }
    }

    public List<PrestamoLibrosResponse> listAllLibrosByEstado(){
        List<PrestamoLibros> lista = prestamoLibrosRepository.findAllByEstado(false);
        return lista.stream().map(libro->{
            PrestamoLibrosResponse response = new PrestamoLibrosResponse();
            response.setId(libro.getId());
            response.setCodigoLibro(libro.getCodigoLibro());
            response.setEstado(libro.getEstado());
            response.setAutor(libro.getAutor());
            response.setNombre(libro.getNombre());
            response.setIsbn(libro.getIsbn());
            return response;
        }).collect(Collectors.toList());
    }


}
