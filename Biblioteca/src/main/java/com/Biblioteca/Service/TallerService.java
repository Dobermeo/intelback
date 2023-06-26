package com.Biblioteca.Service;

import com.Biblioteca.DTO.CursoTaller.*;
import com.Biblioteca.DTO.CursoTaller.reportes.TallerporGeneroResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Exceptions.ResponseNotFoundException;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Models.CursoTaller.Taller.Taller;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Repository.CursoTaller.CursoTallerRepository;
import com.Biblioteca.Repository.CursoTaller.TallerRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TallerService {
    @Autowired
    private TallerRepository tallerRepository;
    @Autowired
    private CursoTallerRepository cursotallerRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public TallerResponse registrarTaller(TallerRequest tallerRequest) {
        Optional<CursoTaller> optional1 = cursotallerRepository.findDistinctByNombreAndFechaInicio(tallerRequest.getNombre(),tallerRequest.getFechaInicio());
        if (!optional1.isPresent()) {
            CursoTaller newcursoTaller = new CursoTaller();
            newcursoTaller.setNombre(tallerRequest.getNombre());
            newcursoTaller.setLugar(tallerRequest.getLugar());
            newcursoTaller.setDescripcion(tallerRequest.getDescripcion());
            newcursoTaller.setResponsable(tallerRequest.getResponsable());
            newcursoTaller.setFechaInicio(tallerRequest.getFechaInicio());
            newcursoTaller.setFechaMaxInscripcion(tallerRequest.getFechaMaxInscripcion());
            newcursoTaller.setFechaFin(tallerRequest.getFechaFin());

            CursoTaller cursoTaller = cursotallerRepository.save(newcursoTaller);
            if (cursoTaller != null) {
                guardarTaller(cursoTaller.getId());
                Optional<Taller> taller = tallerRepository.findByCursoTaller(cursoTaller);

                return new TallerResponse(cursoTaller.getId(),taller.get().getId(), cursoTaller.getNombre(), cursoTaller.getLugar(),
                        cursoTaller.getDescripcion(), cursoTaller.getResponsable(),
                        cursoTaller.getFechaInicio(),cursoTaller.getFechaMaxInscripcion(), cursoTaller.getFechaFin());
            } else {
                throw new BadRequestException("No se pudo guardar el taller" + cursoTaller.getNombre());
            }

        } else {
            throw new BadRequestException("ya registrado UN TALLER con el mismo nombre en la misma fecha de inicio\"");
        }
    }

        private boolean guardarTaller(Long id) {
        Optional<CursoTaller> optional2 = cursotallerRepository.findById(id);
        if (optional2.isPresent()) {
            CursoTaller ct = optional2.get();
            Taller nuevo = new Taller();
            nuevo.setCursoTaller(ct);
            Taller talleres = tallerRepository.save(nuevo);
            if (talleres != null) {
                return true;
            } else {
                throw new BadRequestException("Taller no registrado");
            }
        } else {
            throw new BadRequestException("ya existe un taller con esos datos");
        }
    }


    public boolean actualizartalleres(TallerRequest tallerrequest) {
        Optional<CursoTaller> optionalc = cursotallerRepository.findById(tallerrequest.getId());
        if (optionalc.isPresent()) {
            optionalc.get().setNombre(tallerrequest.getNombre());
            optionalc.get().setLugar(tallerrequest.getLugar());
            optionalc.get().setDescripcion(tallerrequest.getDescripcion());
            optionalc.get().setResponsable(tallerrequest.getResponsable());
            optionalc.get().setFechaInicio(tallerrequest.getFechaInicio());
            optionalc.get().setFechaMaxInscripcion(tallerrequest.getFechaMaxInscripcion());
            optionalc.get().setFechaFin(tallerrequest.getFechaFin());
            try {
                CursoTaller ct = cursotallerRepository.save(optionalc.get());
                if (ct != null) {
                    guardaractualizacion(ct);
                } else {
                    throw new BadRequestException("NO SE ACTUALIZO EL TALLER");
                }
            } catch (Exception ex) {
                throw new BadRequestException("No ACTUALIZADO" + ex);
            }
        } else {
            throw new BadRequestException("NO EXISTE EL TALLER CON ID " + tallerrequest.getId());
        }
        return false;
    }

    private boolean guardaractualizacion(CursoTaller cursoTaller) {
        Optional<Taller> optionalc = tallerRepository.findByCursoTaller(cursoTaller);
        ;
        if (optionalc.isPresent()) {
            try {
                Taller c = tallerRepository.save(optionalc.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("NO SE ACTUALIZO LA TABLA CURSO/ TALLER" + ex);
            }
        }
        throw new BadRequestException("NO EXISTE EL TALLER");
    }


    public boolean actualizartallerconid_taller(TallerRequest tallerrequest) {
        Optional<Taller> optionalc = tallerRepository.findById(tallerrequest.getIdTaller());
        if (optionalc.isPresent()) {
            try {
               Taller c = tallerRepository.save(optionalc.get());
                if (c != null) {
                    guardaractualizaciontalleres(c.getCursoTaller().getId(), tallerrequest.getNombre(), tallerrequest.getLugar(),
                            tallerrequest.getDescripcion(),tallerrequest.getResponsable(),
                            tallerrequest.getFechaInicio(),tallerrequest.getFechaMaxInscripcion(),tallerrequest.getFechaFin());
                } else {
                    throw new BadRequestException("NO SE ACTUALIZO EL TALLER");
                }
            } catch (Exception ex) {
                throw new BadRequestException("No ACTUALIZADO" + ex);
            }
        } else {
            throw new BadRequestException("NO EXISTE EL TALLER CON ID " + tallerrequest.getId());
        }
        return false;
    }


    private boolean guardaractualizaciontalleres(Long idCursotaller, String nombre, String lugar,String descripcion,
                                          String responsable, Date fechaInicio,Date fechaMax, Date fechaFin) {
        Optional<CursoTaller> optionalc = cursotallerRepository.findById(idCursotaller);
        if (optionalc.isPresent()) {
            optionalc.get().setNombre(nombre);
            optionalc.get().setLugar(lugar);
            optionalc.get().setDescripcion(descripcion);
            optionalc.get().setResponsable(responsable);
            optionalc.get().setFechaInicio(fechaInicio);
            optionalc.get().setFechaMaxInscripcion(fechaMax);
            optionalc.get().setFechaFin(fechaFin);
            try {
                CursoTaller c = cursotallerRepository.save(optionalc.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("NO SE ACTUALIZO LA TABLA TALLER" + ex);
            }
        }
        throw new BadRequestException("NO EXISTE EL TALLER");
    }



    public List<TallerResponse> listAllTalleres() {
        List<Taller> taller = tallerRepository.findAll();
        return taller.stream().map(cRequest -> {
            TallerResponse tr = new TallerResponse();
            tr.setId(cRequest.getCursoTaller().getId());
            tr.setNombre(cRequest.getCursoTaller().getNombre());
            tr.setLugar(cRequest.getCursoTaller().getLugar());
            tr.setDescripcion(cRequest.getCursoTaller().getDescripcion());
            tr.setResponsable(cRequest.getCursoTaller().getResponsable());
            tr.setFechaInicio(cRequest.getCursoTaller().getFechaInicio());
            tr.setFechaMaxInscripcion(cRequest.getCursoTaller().getFechaMaxInscripcion());
            tr.setFechaFin(cRequest.getCursoTaller().getFechaFin());
            tr.setIdTaller(cRequest.getId());
            return tr;
        }).collect(Collectors.toList());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public TallerResponse listartallerbyIdTaller(Long id) {
       TallerResponse cr = new TallerResponse();
        Optional<Taller> taller = tallerRepository.findById(id);
        if (taller.isPresent()) {
            Optional<CursoTaller> cursotaller = cursotallerRepository.findById(taller.get().getCursoTaller().getId());
            if (cursotaller.isPresent()) {
                cr.setId(cursotaller.get().getId());
                cr.setNombre(cursotaller.get().getNombre());
                cr.setLugar(cursotaller.get().getLugar());
                cr.setDescripcion(cursotaller.get().getDescripcion());
                cr.setResponsable(cursotaller.get().getResponsable());
                cr.setFechaInicio(cursotaller.get().getFechaInicio());
                cr.setFechaMaxInscripcion(cursotaller.get().getFechaMaxInscripcion());
                cr.setFechaFin(cursotaller.get().getFechaFin());
                cr.setIdTaller(taller.get().getId());
                return cr;
            } else {
                throw new BadRequestException("NO EXISTE UN TALLER ASOCIADO A LA TABLA CURSO/TALLER");
            }
        } else {
            throw new BadRequestException("NO EXISTE EL TALLER CON ID " + id);
        }
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TallerFecha> listaPorft(Date fechaInicio){
        return tallerRepository.findDistinctByFechaInicio(fechaInicio);
    }
    public void deletetallerById(Long id){
        Optional<Taller> optional = tallerRepository.findById(id);
        if(optional.isPresent()){
            tallerRepository.deleteById(id);
        }else {
            throw new BadRequestException("El taller con el id " + id + ", no existe");
        }
    }


    @Transactional
    public boolean agregarClientesalTaller(Long idCliente, Long idTaller){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.isPresent()) {
            Optional<Taller> taller = tallerRepository.findById(idTaller);
            if(taller.isPresent()) {
                Cliente cliid = taller.get().getClientes()
                        .stream()
                        .filter(a -> Objects.equals(a.getId(), idCliente))
                        .findFirst().orElse(null);
                if (cliid==null){
                try{
                    cliente.get().getTalleres().add(taller.get());
                    clienteRepository.save(cliente.get());
                    return true;
                }catch (Exception e){
                    throw new BadRequestException("No se guardo el cliente en el taller");
                }
                } else {
                    throw new BadRequestException("El CLIENTE CON CEDULA " +cliente.get().getPersona().getCedula()+ " ya esta registrado en el taller "+ taller.get().getCursoTaller().getNombre());
                }
            }else{
                throw new BadRequestException("No existe un taller con id" +idTaller);
            }
        }else{
            throw new BadRequestException("NO EXISTE EL CLIENTE CON EL ID: " +idCliente);
        }
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Cliente_tallerResponse listartallerbyClientes(Long id) {
        Cliente_tallerResponse cr = new Cliente_tallerResponse();
        Optional<Taller> taller = tallerRepository.findById(id);
        if (taller.isPresent()) {
            Optional<CursoTaller> cursotaller = cursotallerRepository.findById(taller.get().getCursoTaller().getId());
            if (cursotaller.isPresent()) {
                cr.setId(cursotaller.get().getId());
                cr.setNombre(cursotaller.get().getNombre());
                cr.setLugar(cursotaller.get().getLugar());
                cr.setDescripcion(cursotaller.get().getDescripcion());
                cr.setResponsable(cursotaller.get().getResponsable());
                cr.setFechaInicio(cursotaller.get().getFechaInicio());
                cr.setFechaMaxInscripcion(cursotaller.get().getFechaMaxInscripcion());
                cr.setFechaFin(cursotaller.get().getFechaFin());
                cr.setIdTaller(taller.get().getId());

                List<ListaClientesTallerRequest> list = taller.get().getClientes().stream().map(ac -> {
                    ListaClientesTallerRequest request = new ListaClientesTallerRequest();
                    request.setCedula(ac.getPersona().getCedula());
                    request.setNombres(ac.getPersona().getNombres());
                    request.setApellidos(ac.getPersona().getApellidos());
                    request.setTelefono(ac.getPersona().getTelefono());
                    request.setGenero(ac.getGenero());
                    request.setEstadoCivil(ac.getEstadoCivil());
                    request.setId(ac.getId());
                    return request;
                }).collect(Collectors.toList());

                cr.setListaClientesTallerRequests(list);

                return cr;
            } else {
                throw new BadRequestException("NO EXISTE UN TALLER ASOCIADO A LA TABLA CURSO/TALLER");
            }
        } else {
            throw new BadRequestException("NO EXISTE EL TALLER CON ID " + id);
        }
    }

    @PersistenceContext
    private EntityManager entityManager;


    public BigInteger countarinscritos(Long id) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM taller_clientes tc where tc.taller_id= ?");
        nativeQuery.setParameter(1, id);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public N_clientestallerResponse contarclientes_entaller(Long idTaller) {
        Optional<Taller> taller = tallerRepository.findById(idTaller);
        if (taller.isPresent()) {
            N_clientestallerResponse n = new N_clientestallerResponse();
            BigInteger numerodeparticipantes;
            numerodeparticipantes =countarinscritos(idTaller);
            n.setNumero(numerodeparticipantes.longValue());
            return n;
        }
        else{
            throw new BadRequestException("NO EXISTEN AUN PARTICIPANTES EN EL TALLER: " + idTaller);
        }
    }

    @Transactional
    @Modifying
    public void deleteClientebyIdTaller(Long idTaller, Long idCliente) {
        Optional<Taller> optionalt = tallerRepository.findById(idTaller);
        if (optionalt.isPresent()) {
            Cliente cliid = optionalt.get().getClientes()
                    .stream()
                    .filter(a -> Objects.equals(a.getId(), idCliente))
                    .findFirst()
                    .orElseThrow(() -> new ResponseNotFoundException("Cliente", "id", idCliente + ""));
            try {
                tallerRepository.deleteQuery(idTaller,cliid.getId());
                return;
            } catch (Exception e) {
                throw new BadRequestException("Error al eliminar el cliente con id: ");
            }
        }
        throw new ResponseNotFoundException("Taller", "id", idTaller + "");
    }



    public BigInteger contarporgeneroM(Long id) {
        String generoM="MASCULINO";
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM taller_clientes tc " +
                "join cliente cl on cl.id=tc.cliente_id where tc.taller_id= ? and cl.genero= ?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, generoM);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarporgeneroF(Long id) {
        String generoF="FEMENINO";
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM taller_clientes tc " +
                "join cliente cl on cl.id=tc.cliente_id where tc.taller_id= ? and cl.genero= ?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, generoF);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarporgeneroOtro(Long id) {
        String generoOtro="OTROS";
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM taller_clientes tc " +
                "join cliente cl on cl.id=tc.cliente_id where tc.taller_id= ? and cl.genero= ?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, generoOtro);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public  TallerporGeneroResponse reportetallerporgenero(Long idTaller) {
        Optional<Taller> taller = tallerRepository.findById(idTaller);
        if (taller.isPresent()) {
            TallerporGeneroResponse tr = new TallerporGeneroResponse();
            BigInteger numeroM,numeroF, numeroOtro, total;
            Double porcentajeM,porcentajeF,porcentajeOtro;
            numeroM =contarporgeneroM(idTaller);
            numeroF=contarporgeneroF(idTaller);
            numeroOtro=contarporgeneroOtro(idTaller);
            total=countarinscritos(idTaller);
            porcentajeM=(numeroM.doubleValue()*100)/total.doubleValue();
            porcentajeF=(numeroF.doubleValue()*100)/total.doubleValue();
            porcentajeOtro=(numeroOtro.doubleValue()*100)/total.doubleValue();
            try {
                tr.setN_Masculino(numeroM.longValue());
                tr.setN_Femenino(numeroF.longValue());
                tr.setN_Otro(numeroOtro.longValue());
                tr.setTotal(total.longValue());
                tr.setPorcent_Masculino(porcentajeM);
                tr.setPorcent_Femenino(porcentajeF);
                tr.setPorcent_Otro(porcentajeOtro);
                if(total.longValue()>0) {
                    return tr;
                } else {
                    throw new BadRequestException("NO EXISTEN AUN PARTICIPANTES EN EL TALLER: " + idTaller);
                }
            } catch (Exception e) {
                throw new BadRequestException("El taller  "+idTaller+" aun no tiene participantes");
            }
        }
        throw new ResponseNotFoundException("Taller", "id", idTaller + "");
    }

}