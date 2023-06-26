package com.Biblioteca.Service.Copias;


import com.Biblioteca.DTO.CursoTaller.CursoRequest;
import com.Biblioteca.DTO.CursoTaller.CursoResponse;
import com.Biblioteca.DTO.CursoTaller.reportes.CursoporgeneroResponse;
import com.Biblioteca.DTO.Estadisticas.Datos;
import com.Biblioteca.DTO.Estadisticas.EstadisticasGenero;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteRequest;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteResponse;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.CopiasImpresionesRequest;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.reporte.CopiasClientesporGenero;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Exceptions.ResponseNotFoundException;
import com.Biblioteca.Models.CursoTaller.Curso.Curso;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasCliente;
import com.Biblioteca.Models.Servicio.CopiasImpresiones.CopiasImpresiones;
import com.Biblioteca.Repository.CopiasImpresiones.CopiasClientesRepository;
import com.Biblioteca.Repository.CopiasImpresiones.CopiasImpresionesRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Persona.DatosEstadicticasMesAnio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CopiasService {

    @Autowired
    private CopiasImpresionesRepository copiasImpresionesRepository;

    @Autowired
    private CopiasClientesRepository copiasClientesRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    @Transactional
    public CopiasClienteResponse registroCopiasCliente(CopiasClienteRequest request){

        Optional<Cliente> cliente = clienteRepository.findById(request.getIdCliente());
        if (cliente.isPresent()){
            CopiasImpresiones newCopias= new CopiasImpresiones();
            newCopias.setPagBlanco(request.getPagBlanco());
            newCopias.setPagColor(request.getPagColor());
            newCopias.setPagTotal(request.getPagColor()+ request.getPagBlanco());

                CopiasImpresiones copias=copiasImpresionesRepository.save(newCopias);
               if(copias != null){
                   Optional<CopiasImpresiones> copiasImpres= copiasImpresionesRepository.findById(copias.getId());
                   if(copiasImpres.isPresent()){
                       CopiasCliente newCopiasCliente = new CopiasCliente();
                       newCopiasCliente.setFecha_actual(request.getFecha());
                       newCopiasCliente.setDia((long)request.getFecha().getDate());
                       newCopiasCliente.setMes((long)request.getFecha().getMonth()+1);
                       newCopiasCliente.setAnio((long) request.getFecha().getYear()+1900);
                       newCopiasCliente.setCliente(cliente.get());
                       newCopiasCliente.setCopias(copias);
                       try{
                           copiasClientesRepository.save(newCopiasCliente);
                           return new CopiasClienteResponse(newCopiasCliente.getId(),request.getFecha(), cliente.get().getId(),
                                   copias.getId(), copias.getPagBlanco(), copias.getPagColor(), copias.getPagTotal());
                       }catch (Exception e){
                           throw new BadRequestException("No se guardó el cliente que ocupó servicio copias tbl copias_cliente" +e);
                       }
                   }else{
                       throw new BadRequestException("No existe las copias con id" +copias.getId());
                   }
               }
        }else{
            throw new BadRequestException("No existe un cliente con id" +request.getIdCliente());
        }
        return null;
    }



    public boolean actualizarcopias(CopiasClienteRequest crequest) {
        Optional<Cliente> cliente = clienteRepository.findById(crequest.getIdCliente());
        Optional<CopiasCliente> optionalc = copiasClientesRepository.findById(crequest.getId());
        if (optionalc.isPresent()) {
            optionalc.get().setFecha_actual(crequest.getFecha());
            optionalc.get().setDia((long)crequest.getFecha().getDate());
            optionalc.get().setMes((long)crequest.getFecha().getMonth()+1);
            optionalc.get().setAnio((long)crequest.getFecha().getYear()+1900);
            optionalc.get().setCliente(cliente.get());
            try {
                CopiasCliente c = copiasClientesRepository.save(optionalc.get());
                if (c != null) {
                    guardaractualizacioncopias(c.getCopias().getId(), crequest.getPagBlanco(), crequest.getPagColor());
                } else {
                    throw new BadRequestException("NO SE ACTUALIZO");
                }
            } catch (Exception ex) {
                throw new BadRequestException("REGISTRO NO ACTUALIZADO" + ex);
            }
        } else {
            throw new BadRequestException("NO EXISTE LA COPIA/IMPRESION CON ID " + crequest.getId());
        }
        return false;
    }

    private boolean guardaractualizacioncopias(Long idcopia,Long pagBlanco,Long pagColor) {
        Optional<CopiasImpresiones> optionalc = copiasImpresionesRepository.findById(idcopia);
        if (optionalc.isPresent()) {
            optionalc.get().setPagBlanco(pagBlanco);
            optionalc.get().setPagColor(pagColor);
            optionalc.get().setPagTotal(pagColor+ pagBlanco);
            try {
                CopiasImpresiones c = copiasImpresionesRepository.save(optionalc.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("NO SE ACTUALIZO LA TABLA COPIAS/IMPRESIONES" + ex);
            }
        }
        throw new BadRequestException("NO EXISTE EL REGISTRO DE COPIA/IMPRESION");
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CopiasClienteResponse> listAllCopias() {
        List<CopiasCliente> copias = copiasClientesRepository.findAll();
        return copias.stream().map(cRequest -> {
            CopiasClienteResponse cr = new CopiasClienteResponse();
            cr.setId(cRequest.getId());
            cr.setIdCliente(cRequest.getCliente().getId());
            cr.setIdCopias(cRequest.getCopias().getId());
            cr.setPagBlanco(cRequest.getCopias().getPagBlanco());
            cr.setPagColor(cRequest.getCopias().getPagColor());
            cr.setPagTotal(cRequest.getCopias().getPagTotal());
            cr.setFechaEntrega(ParseFecha(cRequest.getDia()+"",cRequest.getMes()+"",cRequest.getAnio()+""));
            return cr;
        }).collect(Collectors.toList());
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CopiasClienteResponse listarbyIdCopiacliente(Long id) {
        CopiasClienteResponse cr = new CopiasClienteResponse();
        Optional<CopiasCliente> ccli  = copiasClientesRepository.findById(id);
        if (ccli.isPresent()) {
            Optional<CopiasImpresiones> ci = copiasImpresionesRepository.findById(ccli.get().getCopias().getId());
            if (ci.isPresent()) {
                cr.setId(ccli.get().getId());
                cr.setIdCliente(ccli.get().getCliente().getId());
                cr.setFechaEntrega(ParseFecha(ccli.get().getDia()+"",ccli.get().getMes()+"",ccli.get().getAnio()+""));
                cr.setIdCopias(ci.get().getId());
                cr.setPagColor(ci.get().getPagColor());
                cr.setPagBlanco(ci.get().getPagBlanco());
                cr.setPagBlanco(ci.get().getPagBlanco());
                cr.setPagTotal(ci.get().getPagTotal());
                return cr;
            } else {
                throw new BadRequestException("NO EXISTE UN REGISTRO DE COPIAS EN LA TABLA COPIAS/IMPRESIONES");
            }
        } else {
            throw new BadRequestException("NO EXISTE EL REGISTRO CON ID " + id);
        }
    }
    public static Date ParseFecha(String dia,String mes,String anio){
        String fecha= dia+"/"+mes+"/"+anio;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }



    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CopiasClienteResponse> listarbymesandanio(Long mes, Long anio) {
        List<CopiasCliente> copias = copiasClientesRepository.findByMesAndAnio(mes,anio);
        if(!copias.isEmpty()){
        return copias.stream().map(cRequest -> {
            CopiasClienteResponse cr = new CopiasClienteResponse();
            cr.setId(cRequest.getId());
            cr.setIdCliente(cRequest.getCliente().getId());
            /////
            cr.setCedula(cRequest.getCliente().getPersona().getCedula());
            cr.setNombre(cRequest.getCliente().getPersona().getNombres());
            cr.setApellido(cRequest.getCliente().getPersona().getApellidos());

            cr.setIdCopias(cRequest.getCopias().getId());
            cr.setPagBlanco(cRequest.getCopias().getPagBlanco());
            cr.setPagColor(cRequest.getCopias().getPagColor());
            cr.setPagTotal(cRequest.getCopias().getPagTotal());
            cr.setFechaEntrega(ParseFecha(cRequest.getDia()+"",cRequest.getMes()+"",cRequest.getAnio()+""));
            return cr;
        }).collect(Collectors.toList());
        }
        throw new BadRequestException("NO EXISTEN COPIAS EN LA FECHA ESPECIFICADA");
    }

    public void deleteById(Long id) {
        Optional<CopiasCliente> optional = copiasClientesRepository.findById(id);
        if (optional.isPresent()) {
            Optional<CopiasImpresiones> optionalci = copiasImpresionesRepository.findById(optional.get().getCopias().getId());
            if (optionalci.isPresent()) {
                copiasClientesRepository.deleteById(id);
                copiasImpresionesRepository.deleteById(optional.get().getCopias().getId());
            } else {
                throw new BadRequestException("El registro de copias/impresiones con el id " + id + ", no existe");
            }
        } else {
            throw new BadRequestException("El registro de copias cliente con el id " + id + ", no existe");
        }
    }

    @PersistenceContext
    private EntityManager entityManager;


    public BigInteger contar(Long mes, Long anio) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM copias_cliente cc " +
                " where cc.mes= ? and cc.anio=?");
        nativeQuery.setParameter(1, mes);
        nativeQuery.setParameter(2, anio);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    public BigInteger contarporgeneroM(Long mes, Long anio) {
        String generoM="MASCULINO";
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM copias_cliente cc " +
                "join cliente cl on cl.id=cc.id_cliente where cc.mes= ? and cc.anio=? and cl.genero= ?");
        nativeQuery.setParameter(1, mes);
        nativeQuery.setParameter(2, anio);
        nativeQuery.setParameter(3, generoM);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarporgeneroF(Long mes, Long anio) {
        String generoF="FEMENINO";
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM copias_cliente cc " +
                "join cliente cl on cl.id=cc.id_cliente where cc.mes= ? and cc.anio=? and cl.genero= ?");
        nativeQuery.setParameter(1, mes);
        nativeQuery.setParameter(2, anio);
        nativeQuery.setParameter(3, generoF);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarporgeneroOtro(Long mes, Long anio) {
        String generoOtro="OTROS";
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT count(*) FROM copias_cliente cc " +
                "join cliente cl on cl.id=cc.id_cliente where cc.mes= ? and cc.anio=? and cl.genero= ?");
        nativeQuery.setParameter(1, mes);
        nativeQuery.setParameter(2, anio);
        nativeQuery.setParameter(3, generoOtro);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CopiasClientesporGenero reporteporgenero(Long mes, Long anio) {

            CopiasClientesporGenero cr = new CopiasClientesporGenero();
            BigInteger numeroM,numeroF, numeroOtro, total;
            Double porcentajeM,porcentajeF,porcentajeOtro;
            numeroM =contarporgeneroM(mes, anio);
            numeroF=contarporgeneroF(mes, anio);
            numeroOtro=contarporgeneroOtro(mes, anio);
            total=contar(mes, anio);
            porcentajeM=(numeroM.doubleValue()*100)/total.doubleValue();
            porcentajeF=(numeroF.doubleValue()*100)/total.doubleValue();
            porcentajeOtro=(numeroOtro.doubleValue()*100)/total.doubleValue();
            try {
                cr.setN_Masculino(numeroM.longValue());
                cr.setN_Femenino(numeroF.longValue());
                cr.setN_Otro(numeroOtro.longValue());
                cr.setTotal(total.longValue());
                cr.setPorcent_Masculino(porcentajeM);
                cr.setPorcent_Femenino(porcentajeF);
                cr.setPorcent_Otro(porcentajeOtro);
                if(total.longValue()>0) {
                    return cr;
                } else {
                    throw new BadRequestException("NO EXISTEN AUN registro de copias el la fecha: " + mes+"/"+ anio);
                }
            } catch (Exception e) {
                throw new BadRequestException("El registro de copias en la fecha: " + mes+"/"+ anio+" aun no existe");
            }
    }
    public Double calcularPorcentaje(Long total, Long cantidad){
        Double pct= (double)(cantidad*100)/total;
        return Math.round(pct*100)/100.0;

    }

    @Transactional
    public List<DatosEstadicticasMesAnio> listaClientesLibroMesANio(Long mes, Long anio){
        return copiasClientesRepository.findAllByMesandAnio(mes, anio);
    }

    @Transactional
    public EstadisticasGenero estadisticasGeneroCopias(Long mes, Long anio){
        Long numMasculino = copiasImpresionesRepository.countDistinctByGeneroAndMesAndAnio("MASCULINO",mes,anio);

        Long numFemenino = copiasImpresionesRepository.countDistinctByGeneroAndMesAndAnio("FEMENINO",mes,anio);

        Long numOtros = copiasImpresionesRepository.countDistinctByGeneroAndMesAndAnio("OTROS",mes,anio);

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
