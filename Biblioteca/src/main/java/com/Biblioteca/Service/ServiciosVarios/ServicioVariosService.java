package com.Biblioteca.Service.ServiciosVarios;


import com.Biblioteca.DTO.Servicios.ServiciosVarios.Clientes.ServicioVariosClienteRequest;
import com.Biblioteca.DTO.Servicios.ServiciosVarios.ServiciosVariosRequest;
import com.Biblioteca.DTO.Servicios.ServiciosVarios.ServiciosVariosResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.ServiciosVarios.ServiciosVarios;
import com.Biblioteca.Models.Servicio.ServiciosVarios.ServiciosVariosCliente;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.ServiciosVarios.DatosServicioVarioCliente;
import com.Biblioteca.Repository.ServiciosVarios.ServiciosVariosClienteRepository;
import com.Biblioteca.Repository.ServiciosVarios.ServiciosVariosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServicioVariosService {

    @Autowired
    private ServiciosVariosRepository serviciosVariosRepository;

    @Autowired
    private ServiciosVariosClienteRepository serviciosVariosClienteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public boolean registroServiciosVarios(ServiciosVariosRequest request){
        Optional<ServiciosVarios> optional = serviciosVariosRepository.findByDescripcionLikeIgnoreCase(request.getDescripcion());
        if(!optional.isPresent()){
            ServiciosVarios sv = new ServiciosVarios();
            sv.setDescripcion(request.getDescripcion());
            try{
                serviciosVariosRepository.save(sv);
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se guardó el servicio varios" + ex);
            }
        }else{
            throw new BadRequestException("Ya existe un servicio varios con la misma descripción");
        }
    }

    @Transactional
    public boolean updateServicioVarios(ServiciosVariosRequest request){
        Optional<ServiciosVarios> optional = serviciosVariosRepository.findById(request.getId());
        if(optional.isPresent()){
            optional.get().setDescripcion(request.getDescripcion());
            try{
                serviciosVariosRepository.save(optional.get());
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se actualizó el servicio varios" + ex);
            }
        }else{
            throw new BadRequestException("No existe un servicio varios con el id " +request.getId() );
        }
    }

    public List<ServiciosVariosResponse> listAllServiciosVarios(){
        List<ServiciosVarios> lista = serviciosVariosRepository.findAll();
        return lista.stream().map(ser->{
            ServiciosVariosResponse svr = new ServiciosVariosResponse();
            svr.setId(ser.getId());
            svr.setDescripcion(ser.getDescripcion());
            return svr;
        }).collect(Collectors.toList());
    }


    //METODOS PARA SERVICIOS CLIENTES


    @Transactional
    public boolean registroServicioVarioCliente(ServicioVariosClienteRequest request){
        Optional<Cliente> optionalCliente = clienteRepository.findById(request.getIdCliente());
        if(optionalCliente.isPresent()){
            Optional<ServiciosVarios> optionalServiciosVarios = serviciosVariosRepository.findById(request.getIdServicioVario());
            if(optionalServiciosVarios.isPresent()){
                ServiciosVariosCliente svc = new ServiciosVariosCliente();
                svc.setCliente(optionalCliente.get());
                svc.setServiciosVarios(optionalServiciosVarios.get());
                svc.setObservaciones(request.getObservaciones());
                svc.setDiaPrestamo((long) request.getFechaActual().getDate());
                svc.setMesPrestamo((long) request.getFechaActual().getMonth()+1);
                svc.setAnioPrestamo((long) request.getFechaActual().getYear()+1900);
                try {
                    serviciosVariosClienteRepository.save(svc);
                    return true;
                } catch (Exception ex) {
                    throw new BadRequestException("No se guardó el cliente/servicioVario" + ex);
                }
            }else{
                throw new BadRequestException("No existe un servicio varios con el id " +request.getIdServicioVario() );
            }
        }else{
            throw new BadRequestException("No existe un cliente con el id " +request.getIdCliente() );
        }
    }

    @Transactional
    public boolean updateServicioVarioCliente(ServicioVariosClienteRequest request){
        Optional<ServiciosVariosCliente> optional = serviciosVariosClienteRepository.findById(request.getId());
        if(optional.isPresent()){
            optional.get().setObservaciones(request.getObservaciones());
            try {
                serviciosVariosClienteRepository.save(optional.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("No se actualizó el cliente/servicioVario" + ex);
            }
        }else{
            throw new BadRequestException("No existe un servicioVario/cliente  con el id " +request.getId() );
        }
    }

    @Transactional
    public List<DatosServicioVarioCliente> listaServicioVariosCiente(Long mes, Long anio){
        return serviciosVariosClienteRepository.findAllByMesAndAnio(mes, anio);
    }
}
