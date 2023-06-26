package com.Biblioteca.Service.Computo;

import com.Biblioteca.DTO.Computo.ComputoClienteHoraFinRequest;
import com.Biblioteca.DTO.Computo.ComputoClienteHoraFinResponse;
import com.Biblioteca.DTO.Computo.ComputoClienteRequest;
import com.Biblioteca.DTO.Computo.ComputoClienteResponse;
import com.Biblioteca.DTO.Estadisticas.Datos;
import com.Biblioteca.DTO.Estadisticas.EstadisticasGenero;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Persona.Persona;
import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Models.Servicio.CentroComputo.InventarioComputo;
import com.Biblioteca.Repository.Computo.ComputoClienteRepository;
import com.Biblioteca.Repository.Computo.InventarioComputoRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ComputoClienteService {

    @Autowired
    private InventarioComputoRepository inventarioComputoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ComputoClienteRepository computoClienteRepository;


    @Transactional
    public boolean registroComputoCliente(ComputoClienteRequest request){
        Optional<Cliente> cliente = clienteRepository.findById(request.getIdCliente());
        if(cliente.isPresent()){
            Optional<InventarioComputo> inventarioComputo = inventarioComputoRepository.findById(request.getIdInventario());
            if(inventarioComputo.isPresent()){
                if(inventarioComputo.get().getEstado()==false) {
                    ComputoCliente newComputoCliente = new ComputoCliente();
                    newComputoCliente.setDescripcion(request.getDescripcion());
                    newComputoCliente.setHoraInicio(request.getHoraInicio());
                    newComputoCliente.setHoraFin(request.getHoraFin());
                    newComputoCliente.setCliente(cliente.get());
                    newComputoCliente.setInventario(inventarioComputo.get());
                    newComputoCliente.setDia((long) request.getFecha().getDate());
                    newComputoCliente.setMes((long) request.getFecha().getMonth()+1);
                    newComputoCliente.setAnio((long) request.getFecha().getYear()+1900);
                    try {
                        computoClienteRepository.save(newComputoCliente);
                        return true;
                    } catch (Exception ex) {
                        throw new BadRequestException("No se guardó el cliente/computo" + ex);
                    }
                }else {
                    throw new BadRequestException("EL computador esta en prestamo" + request.getIdInventario());
                }
            }else {
                throw new BadRequestException("No existe computador con id" + request.getIdInventario());
            }
        }else {
            throw new BadRequestException("No existe cliente con id" + request.getIdCliente());
        }
    }


    @Transactional
    public List<ComputoClienteResponse> listAllPrestamos(){
        List<ComputoCliente> listaCC= computoClienteRepository.findAll();
        return listaCC.stream().map(computoCliente -> {
            ComputoClienteResponse response = new ComputoClienteResponse();
            Optional<Cliente> cliente = clienteRepository.findById(computoCliente.getCliente().getId());
            if (cliente.isPresent()) {
                Optional<Persona> persona = personaRepository.findById(cliente.get().getPersona().getId());
                if(persona.isPresent()){
                        Optional<InventarioComputo> inventarioComputo = inventarioComputoRepository.findById(computoCliente.getInventario().getId());
                        if(inventarioComputo.isPresent()){
                            Persona p= persona.get();
                            Cliente c = cliente.get();
                            InventarioComputo i = inventarioComputo.get();
                            response.setIdPrestamo(computoCliente.getId());
                            response.setIdPersona(p.getId());
                            response.setIdCliente(c.getId());
                            response.setDescripcion(computoCliente.getDescripcion());
                            response.setHoraInicio(computoCliente.getHoraInicio());
                            response.setHoraFin(computoCliente.getHoraFin());
                            response.setCedula(p.getCedula());
                            response.setApellidos(p.getApellidos());
                            response.setNombres(p.getNombres());
                            response.setFechaNacimiento(c.getFechaNacimiento());
                            response.setEdad(c.getEdad());
                            response.setGenero(c.getGenero());
                            response.setTelefono(p.getTelefono());
                            response.setEmail(p.getEmail());
                            response.setEstadoCivil(c.getEstadoCivil());
                            response.setDiscapacidad(c.getDiscapacidad());
                            response.setNombreResponsable(c.getNombreResponsable());
                            response.setTelefonoResponsbale(c.getTelefonoResponsbale());
                            response.setBarrio(c.getUbicacion().getBarrio().getBarrio());
                            response.setParroquia(c.getUbicacion().getParroquia().getParroquia());
                            response.setCanton(c.getUbicacion().getCanton().getCanton());
                            response.setProvincia(c.getUbicacion().getProvincia().getProvincia());
                            response.setIdComputador(computoCliente.getInventario().getId());
                            response.setNumero(i.getNumero());
                            response.setRam(i.getRam());
                            response.setDiscoDuro(i.getDiscoDuro());
                            response.setProcesador(i.getProcesador());
                            response.setEstado(i.getEstado());
                            response.setEstadoPrestamo(i.getEstadoPrestamo());
                            response.setObservacionesComputador(i.getObservacionesComputador());
                            return response;
                        }else{
                            throw new BadRequestException("No existe computador con id " + computoCliente.getInventario().getId());
                        }
                }else{
                    throw new BadRequestException("No existe persona con id asociada al cliente" + cliente.get().getId());
                }
            }else{
                throw new BadRequestException("No existe cliente con id" + computoCliente.getCliente().getId());
            }
        }).collect(Collectors.toList());
    }


    @Transactional
    public ComputoClienteHoraFinResponse listHoraFin(Long id){
        Optional<ComputoCliente> cc = computoClienteRepository.findById(id);
        if(cc.isPresent()){
            ComputoClienteHoraFinResponse response = new ComputoClienteHoraFinResponse();
            if(cc.get().getHoraFin().isEmpty()){
                response.setHoraFin("0");
                return response;
            }else{
                response.setHoraFin(cc.get().getHoraFin());
                return response;
            }
        }else{
            throw new BadRequestException("No existe registro con id" + id);
        }
    }

    @Transactional
    public List<DatosEstadicticasMesAnio> listaClientesLibroMesANio(Long mes, Long anio){
        return computoClienteRepository.findAllByMesandAnio(mes, anio);
    }

    @Transactional
    public boolean updateComputoCliente(ComputoClienteHoraFinRequest request){
        Optional<ComputoCliente> cc = computoClienteRepository.findById(request.getId());
        if(cc.isPresent()){
            cc.get().setHoraFin(request.getHoraFin());
            try {
                ComputoCliente ccli= computoClienteRepository.save(cc.get());
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se actualizó la hora de fin" + ex);
            }
        }else{
            throw new BadRequestException("No existe registro con id" + request.getId());
        }
    }
    @Transactional
    public List<ComputoClienteResponse> listAllPrestamosHoraFin(){

        List<ComputoCliente> listaCC= computoClienteRepository.findAllByHoraFin("0");
        return listaCC.stream().map(computoCliente -> {
            ComputoClienteResponse response = new ComputoClienteResponse();
            Optional<Cliente> cliente = clienteRepository.findById(computoCliente.getCliente().getId());
            if (cliente.isPresent()) {
                Optional<Persona> persona = personaRepository.findById(cliente.get().getPersona().getId());
                if(persona.isPresent()){
                    Optional<InventarioComputo> inventarioComputo = inventarioComputoRepository.findById(computoCliente.getInventario().getId());
                    if(inventarioComputo.isPresent()){
                        Persona p= persona.get();
                        Cliente c = cliente.get();
                        InventarioComputo i = inventarioComputo.get();
                        response.setIdPrestamo(computoCliente.getId());
                        response.setIdPersona(p.getId());
                        response.setIdCliente(c.getId());
                        response.setDescripcion(computoCliente.getDescripcion());
                        response.setHoraInicio(computoCliente.getHoraInicio());
                        response.setHoraFin(computoCliente.getHoraFin());
                        response.setCedula(p.getCedula());
                        response.setApellidos(p.getApellidos());
                        response.setNombres(p.getNombres());
                        response.setFechaNacimiento(c.getFechaNacimiento());
                        response.setEdad(c.getEdad());
                        response.setGenero(c.getGenero());
                        response.setTelefono(p.getTelefono());
                        response.setEmail(p.getEmail());
                        response.setEstadoCivil(c.getEstadoCivil());
                        response.setDiscapacidad(c.getDiscapacidad());
                        response.setNombreResponsable(c.getNombreResponsable());
                        response.setTelefonoResponsbale(c.getTelefonoResponsbale());
                        response.setBarrio(c.getUbicacion().getBarrio().getBarrio());
                        response.setParroquia(c.getUbicacion().getParroquia().getParroquia());
                        response.setCanton(c.getUbicacion().getCanton().getCanton());
                        response.setProvincia(c.getUbicacion().getProvincia().getProvincia());
                        response.setIdComputador(computoCliente.getInventario().getId());
                        response.setNumero(i.getNumero());
                        response.setRam(i.getRam());
                        response.setDiscoDuro(i.getDiscoDuro());
                        response.setProcesador(i.getProcesador());
                        response.setEstado(i.getEstado());
                        response.setEstadoPrestamo(i.getEstadoPrestamo());
                        response.setObservacionesComputador(i.getObservacionesComputador());
                        return response;
                    }else{
                        throw new BadRequestException("No existe computador con id " + computoCliente.getInventario().getId());
                    }
                }else{
                    throw new BadRequestException("No existe persona con id asociada al cliente" + cliente.get().getId());
                }
            }else{
                throw new BadRequestException("No existe cliente con id" + computoCliente.getCliente().getId());
            }
        }).collect(Collectors.toList());
    }

    public void deleteById(Long id){
        Optional<ComputoCliente> cc = computoClienteRepository.findById(id);
        if(cc.isPresent()){
           computoClienteRepository.deleteById(id);
        }else{
            throw new BadRequestException("No existe registro con id " + id);
        }
    }


    public Double calcularPorcentaje(Long total, Long cantidad){
        Double pct= (double)(cantidad*100)/total;
        return Math.round(pct*100)/100.0;

    }

    @Transactional
    public EstadisticasGenero estadisticasGeneroComputo(Long mes, Long anio){
        Long numMasculino = computoClienteRepository.countDistinctByGeneroAndMesPrestamoAndAnioPrestamo("MASCULINO",mes,anio);

        Long numFemenino = computoClienteRepository.countDistinctByGeneroAndMesPrestamoAndAnioPrestamo("FEMENINO",mes,anio);

        Long numOtros = computoClienteRepository.countDistinctByGeneroAndMesPrestamoAndAnioPrestamo("OTROS",mes,anio);

        Long total = numMasculino+numFemenino+numOtros;

        Datos datosMas= new Datos();
        datosMas.setNum(numMasculino);
        datosMas.setPct(calcularPorcentaje(total,numMasculino));

        Datos datosFemenino= new Datos();
        datosFemenino.setNum(numFemenino);
        datosFemenino.setPct(calcularPorcentaje(total,numFemenino));

        Datos datosOtros= new Datos();
        datosOtros.setNum(numOtros);
        datosOtros.setPct(calcularPorcentaje(total,numOtros));


        EstadisticasGenero e = new EstadisticasGenero();
        e.setMasculino(datosMas);
        e.setFemenino(datosFemenino);
        e.setOtros(datosOtros);
        e.setMes(mes);
        e.setAnio(anio);
        e.setTotal(total);
        return e;

    }
}
