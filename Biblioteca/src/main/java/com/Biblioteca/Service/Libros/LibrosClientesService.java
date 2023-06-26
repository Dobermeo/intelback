package com.Biblioteca.Service.Libros;


import com.Biblioteca.DTO.Estadisticas.Datos;
import com.Biblioteca.DTO.Estadisticas.EstadisticasGenero;
import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.LibrosClientesRequest;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibroCliente;
import com.Biblioteca.Models.Servicio.PrestamoLibros.PrestamoLibros;
import com.Biblioteca.Repository.Libros.LibroClienteRepository;
import com.Biblioteca.Repository.Libros.ListaLibrosPrestamo;
import com.Biblioteca.Repository.Libros.PrestamoLibrosRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LibrosClientesService {

    @Autowired
    private LibroClienteRepository libroClienteRepository;


    //LIBROS
    @Autowired
    private PrestamoLibrosRepository prestamoLibrosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public boolean registroPrestamos(LibrosClientesRequest request) {
        Optional<Cliente> cliente = clienteRepository.findById(request.getIdCliente());
        if (cliente.isPresent()) {
            Optional<PrestamoLibros> libro = prestamoLibrosRepository.findById(request.getIdLibro());
            if (libro.isPresent()) {
                if (libro.get().getEstado()==false) {
                    PrestamoLibroCliente newLibroCliente = new PrestamoLibroCliente();
                    newLibroCliente.setDiaPrestamo((long) request.getFechaEntrega().getDate());
                    newLibroCliente.setMesPrestamo((long) request.getFechaEntrega().getMonth() + 1);
                    newLibroCliente.setAnioPrestamo((long) request.getFechaEntrega().getYear() + 1900);
                    newLibroCliente.setDiaDev((long) request.getFechaDev().getDate());
                    newLibroCliente.setMesDev((long) request.getFechaDev().getMonth() + 1);
                    newLibroCliente.setAnioDev((long) request.getFechaDev().getYear() + 1900);
                    newLibroCliente.setObservacionesEntrega(request.getObservacionesEntrega());
                    newLibroCliente.setCliente(cliente.get());
                    newLibroCliente.setPrestamo(libro.get());
                    try {
                        libroClienteRepository.save(newLibroCliente);
                        return true;
                    } catch (Exception ex) {
                        throw new BadRequestException("No se guardó el cliente/libro" + ex);
                    }
                } else {
                   throw new BadRequestException("El libro está en préstamo");

                }
            } else {
                throw new BadRequestException("No existe un libro con id" + request.getIdLibro());
            }

        } else {
            throw new BadRequestException("No existe cliente con id" + request.getIdCliente());
        }
    }

    public boolean updatePrestamo(LibrosClientesRequest request){
        Optional<PrestamoLibroCliente> optional = libroClienteRepository.findById(request.getIdPrestao());
        if (optional.isPresent()){

                        optional.get().setDiaPrestamo((long) request.getFechaEntrega().getDate());
                        optional.get().setMesPrestamo((long) request.getFechaEntrega().getMonth() + 1);
                        optional.get().setAnioPrestamo((long) request.getFechaEntrega().getYear() + 1900);
                        optional.get().setDiaDev((long) request.getFechaDev().getDate());
                        optional.get().setMesDev((long) request.getFechaDev().getMonth() + 1);
                        optional.get().setAnioDev((long) request.getFechaDev().getYear() + 1900);
                        optional.get().setObservacionesEntrega(request.getObservacionesEntrega());
                        optional.get().setObservacionesDev(request.getObservacionesDev());
                        try {
                            PrestamoLibroCliente p=libroClienteRepository.save(optional.get());
                            return true;
                        } catch (Exception ex) {
                            throw new BadRequestException("No se actualizo el cliente/libro" + ex);
                        }




        }else {
            throw new BadRequestException("No existe prestamo con id" + request.getIdPrestao());
        }

    }

    @Transactional
    public List<ListaLibrosPrestamo> listaLbrosEnPrestamo(){
        return libroClienteRepository.findAllByEstado(false);
    }

    @Transactional
    public List<DatosEstadicticasMesAnio> listaClientesLibroMesANio(Long mes, Long anio){
        return libroClienteRepository.findAllByMesAndAnio(mes, anio);
    }


    public Double calcularPorcentaje(Long total, Long cantidad){
        Double pct= (double)(cantidad*100)/total;
        return Math.round(pct*100)/100.0;

    }

    @Transactional
    public EstadisticasGenero estadisticasGeneroLibros(Long mes, Long anio){
        Long numMasculino = libroClienteRepository.countDistinctByGeneroAndMesPrestamoAndAnioPrestamo("MASCULINO",mes,anio);

        Long numFemenino = libroClienteRepository.countDistinctByGeneroAndMesPrestamoAndAnioPrestamo("FEMENINO",mes,anio);

        Long numOtros = libroClienteRepository.countDistinctByGeneroAndMesPrestamoAndAnioPrestamo("OTROS",mes,anio);

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
