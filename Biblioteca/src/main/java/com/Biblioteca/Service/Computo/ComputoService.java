package com.Biblioteca.Service.Computo;


import com.Biblioteca.DTO.Computo.InventarioRequest;
import com.Biblioteca.DTO.Computo.InventarioResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Servicio.CentroComputo.InventarioComputo;
import com.Biblioteca.Repository.Computo.ComputoClienteRepository;
import com.Biblioteca.Repository.Computo.InventarioComputoRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ComputoService {
    @Autowired
    private InventarioComputoRepository inventarioComputoRepository;


    public boolean registrarInventario(InventarioRequest request){
        Optional<InventarioComputo> optional = inventarioComputoRepository.findByNumero(request.getNumero());
        if(!optional.isPresent()) {
            InventarioComputo inventarioComputo = new InventarioComputo();
            inventarioComputo.setDiscoDuro(request.getDiscoDuro());
            inventarioComputo.setEstado(request.getEstado());
            inventarioComputo.setNumero(request.getNumero());
            inventarioComputo.setProcesador(request.getProcesador());
            inventarioComputo.setRam(request.getRam());
            inventarioComputo.setEstadoPrestamo(request.getEstadoPrestamo());
            inventarioComputo.setObservacionesComputador(request.getObservacionesComputador());
            try{
                inventarioComputoRepository.save(inventarioComputo);
                return true;
            }catch (Exception e){
                throw new BadRequestException("No se registró el computador" +e);
            }
        }else{
            throw new BadRequestException("Ya existe un computador con ese numero");
        }
    }

    public boolean actualizarInventario(InventarioRequest request){
        Optional<InventarioComputo> optional = inventarioComputoRepository.findById(request.getId());
        if(optional.isPresent()) {
            optional.get().setDiscoDuro(request.getDiscoDuro());
            optional.get().setEstado(request.getEstado());
            optional.get().setProcesador(request.getProcesador());
            optional.get().setRam(request.getRam());
            optional.get().setEstadoPrestamo(request.getEstadoPrestamo());
            optional.get().setObservacionesComputador(request.getObservacionesComputador());
           try{
               inventarioComputoRepository.save(optional.get());
               return true;
           }catch (Exception ex) {
               throw new BadRequestException("No se actualizó el computador" + ex);
           }
        }else{
            throw new BadRequestException("No existe un computador con número " +request.getId() );
        }
    }

    public boolean actualizarInventarioEstadoPrestamo(InventarioRequest request){
        Optional<InventarioComputo> optional = inventarioComputoRepository.findById(request.getId());
        if(optional.isPresent()) {
            optional.get().setEstadoPrestamo(request.getEstadoPrestamo());
            try{
                inventarioComputoRepository.save(optional.get());
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se actualizó el estado de prestamo del computador" + ex);
            }
        }else{
            throw new BadRequestException("No existe un computador con número " +request.getId() );
        }
    }


    public List<InventarioResponse> listAllInventario(){
        List<InventarioComputo> inventarioComputo = inventarioComputoRepository.findAll();
        return inventarioComputo.stream().map(request->{
            InventarioResponse response = new InventarioResponse();
            response.setId(request.getId());
            response.setEstado(request.getEstado());
            response.setProcesador(request.getProcesador());
            response.setNumero(request.getNumero());
            response.setRam(request.getRam());
            response.setDiscoDuro(request.getDiscoDuro());
            response.setEstadoPrestamo(request.getEstadoPrestamo());
            response.setObservacionesComputador(request.getObservacionesComputador());
            return response;
        }).collect(Collectors.toList());
    }


    public List<InventarioResponse> listAllInventarioByEstado(Boolean estado){
        List<InventarioComputo> inventarioComputo = inventarioComputoRepository.findAllByEstado(estado);
        return inventarioComputo.stream().map(request->{
            InventarioResponse response = new InventarioResponse();
            response.setId(request.getId());
            response.setEstado(request.getEstado());
            response.setProcesador(request.getProcesador());
            response.setNumero(request.getNumero());
            response.setRam(request.getRam());
            response.setDiscoDuro(request.getDiscoDuro());
            response.setEstadoPrestamo(request.getEstadoPrestamo());
            response.setObservacionesComputador(request.getObservacionesComputador());
            return response;
        }).collect(Collectors.toList());
    }


    public InventarioResponse inventarioByNumero(Long numero){
        InventarioResponse response = new InventarioResponse();
        Optional<InventarioComputo> optional = inventarioComputoRepository.findByNumero(numero);
        if (optional.isPresent()){
            response.setId(optional.get().getId());
            response.setEstado(optional.get().getEstado());
            response.setProcesador(optional.get().getProcesador());
            response.setNumero(optional.get().getNumero());
            response.setRam(optional.get().getRam());
            response.setDiscoDuro(optional.get().getDiscoDuro());
            response.setEstadoPrestamo(optional.get().getEstadoPrestamo());
            response.setObservacionesComputador(optional.get().getObservacionesComputador());
            return response;
        }else{
            throw new BadRequestException("No existe un computador con número " +numero);
        }
    }

    public InventarioResponse inventarioById(Long id){
        InventarioResponse response = new InventarioResponse();
        Optional<InventarioComputo> optional = inventarioComputoRepository.findById(id);
        if (optional.isPresent()){
            response.setId(optional.get().getId());
            response.setEstado(optional.get().getEstado());
            response.setProcesador(optional.get().getProcesador());
            response.setNumero(optional.get().getNumero());
            response.setRam(optional.get().getRam());
            response.setDiscoDuro(optional.get().getDiscoDuro());
            response.setEstadoPrestamo(optional.get().getEstadoPrestamo());
            response.setObservacionesComputador(optional.get().getObservacionesComputador());
            return response;
        }else{
            throw new BadRequestException("No existe un comutador con id " +id);
        }
    }





}
