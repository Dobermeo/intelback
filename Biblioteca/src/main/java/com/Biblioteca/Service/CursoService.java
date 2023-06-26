package com.Biblioteca.Service;

import com.Biblioteca.DTO.CursoTaller.*;
import com.Biblioteca.DTO.CursoTaller.reportes.CursoporgeneroResponse;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Exceptions.ResponseNotFoundException;
import com.Biblioteca.Models.CursoTaller.Curso.Curso;
import com.Biblioteca.Models.CursoTaller.CursoTaller;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Repository.CursoTaller.CursoRepository;
import com.Biblioteca.Repository.CursoTaller.CursoTallerRepository;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private CursoTallerRepository cursotallerRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FechaService fechaService;

    @Transactional
    public CursoResponse registrarCurso(CursoRequest cursoRequest) {
        Optional<CursoTaller> optional1 = cursotallerRepository.findByNombreAndFechaInicio(cursoRequest.getNombre(), cursoRequest.getFechaInicio());
        if (!optional1.isPresent()) {
            CursoTaller newcursoTaller = new CursoTaller();
            newcursoTaller.setNombre(cursoRequest.getNombre());
            newcursoTaller.setLugar(cursoRequest.getLugar());
            newcursoTaller.setDescripcion(cursoRequest.getDescripcion());
            newcursoTaller.setResponsable(cursoRequest.getResponsable());
            newcursoTaller.setFechaInicio(cursoRequest.getFechaInicio());
            newcursoTaller.setFechaMaxInscripcion(cursoRequest.getFechaMaxInscripcion());
            newcursoTaller.setFechaFin(cursoRequest.getFechaFin());

            CursoTaller cursoTaller = cursotallerRepository.save(newcursoTaller);
            if (cursoTaller != null) {
                guardarCurso(cursoTaller.getId(), cursoRequest.getActividades(), cursoRequest.getMateriales(), cursoRequest.getNumParticipantes());
                Optional<Curso> curso = cursoRepository.findByCursoTaller(cursoTaller);

                return new CursoResponse(cursoTaller.getId(), curso.get().getId(), cursoTaller.getNombre(), cursoTaller.getLugar(),
                        cursoTaller.getDescripcion(), cursoTaller.getResponsable(),
                        cursoTaller.getFechaInicio(), cursoTaller.getFechaMaxInscripcion(), cursoTaller.getFechaFin(), curso.get().getActividades(),
                        curso.get().getMateriales(), curso.get().getNumParticipantes());
            } else {
                throw new BadRequestException("No se pudo guardar el curso" + cursoTaller.getNombre());
            }

        } else {
            throw new BadRequestException("ya registrado un curso con el mismo nombre en la misma fecha de inicio");
        }
    }

    private boolean guardarCurso(Long id, String actividades, String materiales, Long numParticipantes) {
        Optional<CursoTaller> optional2 = cursotallerRepository.findById(id);
        if (optional2.isPresent()) {
            CursoTaller ct = optional2.get();
            Curso nuevo = new Curso();
            nuevo.setActividades(actividades);
            nuevo.setMateriales(materiales);
            nuevo.setNumParticipantes(numParticipantes);
            nuevo.setCursoTaller(ct);
            Curso cursos = cursoRepository.save(nuevo);
            if (cursos != null) {
                return true;
            } else {
                throw new BadRequestException("Curso no registrado");
            }
        } else {
            throw new BadRequestException("ya existe un curso " + id);
        }
    }


    public boolean actualizarcursosconid_cursotaller(CursoRequest cursorequest) {
        Optional<CursoTaller> optionalc = cursotallerRepository.findById(cursorequest.getId());
        if (optionalc.isPresent()) {
            optionalc.get().setNombre(cursorequest.getNombre());
            optionalc.get().setLugar(cursorequest.getLugar());
            optionalc.get().setDescripcion(cursorequest.getDescripcion());
            optionalc.get().setResponsable(cursorequest.getResponsable());
            optionalc.get().setFechaInicio(cursorequest.getFechaInicio());
            optionalc.get().setFechaMaxInscripcion(cursorequest.getFechaMaxInscripcion());
            optionalc.get().setFechaFin(cursorequest.getFechaFin());
            try {
                CursoTaller ct = cursotallerRepository.save(optionalc.get());
                if (ct != null) {
                    guardaractualizacion(ct, cursorequest.getActividades(), cursorequest.getMateriales(), cursorequest.getNumParticipantes());
                } else {
                    throw new BadRequestException("NO SE ACTUALIZO EL CURSO");
                }
            } catch (Exception ex) {
                throw new BadRequestException("No ACTUALIZADO" + ex);
            }
        } else {
            throw new BadRequestException("NO EXISTE EL CURSO CON ID " + cursorequest.getId());
        }
        return false;
    }

    private boolean guardaractualizacion(CursoTaller cursoTaller, String actividades, String materiales, Long numparticipantes) {
        Optional<Curso> optionalc = cursoRepository.findByCursoTaller(cursoTaller);
        ;
        if (optionalc.isPresent()) {
            optionalc.get().setActividades(actividades);
            optionalc.get().setMateriales(materiales);
            optionalc.get().setNumParticipantes(numparticipantes);
            try {
                Curso c = cursoRepository.save(optionalc.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("NO SE ACTUALIZO LA TABLA CURSO/ TALLER" + ex);
            }
        }
        throw new BadRequestException("NO EXISTE EL CURSO");
    }


    public boolean actualizarcursosconid_curso(CursoRequest cursorequest) {
        Optional<Curso> optionalc = cursoRepository.findById(cursorequest.getIdCurso());
        if (optionalc.isPresent()) {
            optionalc.get().setActividades(cursorequest.getActividades());
            optionalc.get().setMateriales(cursorequest.getMateriales());
            cursorequest.getNumParticipantes();
            BigInteger nminimoparticipantes;
            nminimoparticipantes = count1(cursorequest.getIdCurso());
            System.out.println(nminimoparticipantes + " participantes");
            if(cursorequest.getNumParticipantes()>=nminimoparticipantes.longValue()){
                optionalc.get().setNumParticipantes(cursorequest.getNumParticipantes());
            try {
                Curso c = cursoRepository.save(optionalc.get());
                if (c != null) {
                    guardaractualizacion2(c.getCursoTaller().getId(), cursorequest.getNombre(), cursorequest.getLugar(),
                            cursorequest.getDescripcion(), cursorequest.getResponsable(),
                            cursorequest.getFechaInicio(), cursorequest.getFechaMaxInscripcion(), cursorequest.getFechaFin());
                } else {
                    throw new BadRequestException("NO SE ACTUALIZO EL CURSO");
                }
            } catch (Exception ex) {
                throw new BadRequestException("No ACTUALIZADO" + ex);
            }

        } else {
            throw new BadRequestException("El NUMERO DE PARTICIPANTES NO PUEDE SER MENOR A LOS "+nminimoparticipantes.longValue()+" INSCRITOS ACTUALMENTE");
        }
        } else {
            throw new BadRequestException("NO EXISTE EL CURSO CON ID " + cursorequest.getId());
        }
        return false;
    }


    private boolean guardaractualizacion2(Long idCursotaller, String nombre, String lugar, String descripcion,
                                          String responsable, Date fechaInicio, Date fechaMax, Date fechaFin) {
        Optional<CursoTaller> optionalc = cursotallerRepository.findById(idCursotaller);
        if (optionalc.isPresent()) {
            optionalc.get().setNombre(nombre);
            optionalc.get().setLugar(lugar);
            optionalc.get().setDescripcion(descripcion);
//            optionalc.get().setObservaciones(observaciones);
            optionalc.get().setResponsable(responsable);
            optionalc.get().setFechaInicio(fechaInicio);
            optionalc.get().setFechaMaxInscripcion(fechaMax);
            optionalc.get().setFechaFin(fechaFin);
            try {
                CursoTaller c = cursotallerRepository.save(optionalc.get());
                return true;
            } catch (Exception ex) {
                throw new BadRequestException("NO SE ACTUALIZO LA TABLA CURSO" + ex);
            }
        }
        throw new BadRequestException("NO EXISTE EL CURSO");
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CursoResponse> listAllCursos() {
        List<Curso> curso = cursoRepository.findAll();
        return curso.stream().map(cRequest -> {
            CursoResponse cr = new CursoResponse();
            cr.setId(cRequest.getCursoTaller().getId());
            cr.setNombre(cRequest.getCursoTaller().getNombre());
            cr.setLugar(cRequest.getCursoTaller().getLugar());
            cr.setDescripcion(cRequest.getCursoTaller().getDescripcion());
//            cr.setObservaciones(cRequest.getCursoTaller().getObservaciones());
            cr.setResponsable(cRequest.getCursoTaller().getResponsable());
            cr.setFechaInicio(cRequest.getCursoTaller().getFechaInicio());
            cr.setFechaMaxInscripcion(cRequest.getCursoTaller().getFechaMaxInscripcion());
            cr.setFechaFin(cRequest.getCursoTaller().getFechaFin());
            cr.setIdCurso(cRequest.getId());
            cr.setActividades(cRequest.getActividades());
            cr.setMateriales(cRequest.getMateriales());
            cr.setNumParticipantes(cRequest.getNumParticipantes());
            return cr;
        }).collect(Collectors.toList());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CursoResponse listarcursobyIdCursoTaller(Long id) {
        CursoResponse cr = new CursoResponse();
        Optional<CursoTaller> ct = cursotallerRepository.findById(id);
        if (ct.isPresent()) {
            Optional<Curso> curso = cursoRepository.findByCursoTaller(ct.get());
            if (curso.isPresent()) {
                cr.setId(ct.get().getId());
                cr.setNombre(ct.get().getNombre());
                cr.setLugar(ct.get().getLugar());
                cr.setDescripcion(ct.get().getDescripcion());
//                cr.setObservaciones(ct.get().getObservaciones());
                cr.setResponsable(ct.get().getResponsable());
                cr.setFechaInicio(ct.get().getFechaInicio());
                cr.setFechaMaxInscripcion(ct.get().getFechaMaxInscripcion());
                cr.setFechaFin(ct.get().getFechaFin());
                cr.setIdCurso(curso.get().getId());
                cr.setActividades(curso.get().getActividades());
                cr.setMateriales(curso.get().getMateriales());
                cr.setNumParticipantes(curso.get().getNumParticipantes());
                return cr;
            } else {
                throw new BadRequestException("NO EXISTE EL  ID " + id + "EN LA TABLA CURSO/TALLER");
            }
        } else {
            throw new BadRequestException("NO EXISTE UN CURSO DESDE LA TABLA CURSO/TALLER");
        }
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CursoResponse listarcursobyIdCurso(Long id) {
        CursoResponse cr = new CursoResponse();
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            Optional<CursoTaller> cursotaller = cursotallerRepository.findById(curso.get().getCursoTaller().getId());
            if (cursotaller.isPresent()) {
                cr.setId(cursotaller.get().getId());
                cr.setNombre(cursotaller.get().getNombre());
                cr.setLugar(cursotaller.get().getLugar());
                cr.setDescripcion(cursotaller.get().getDescripcion());
//                cr.setObservaciones(cursotaller.get().getObservaciones());
                cr.setResponsable(cursotaller.get().getResponsable());
                cr.setFechaInicio(cursotaller.get().getFechaInicio());
                cr.setFechaMaxInscripcion(cursotaller.get().getFechaMaxInscripcion());
                cr.setFechaFin(cursotaller.get().getFechaFin());
                cr.setIdCurso(curso.get().getId());
                cr.setActividades(curso.get().getActividades());
                cr.setMateriales(curso.get().getMateriales());
                cr.setNumParticipantes(curso.get().getNumParticipantes());
                return cr;
            } else {
                throw new BadRequestException("NO EXISTE UN CURSO ASOCIADO A LA TABLA CURSO/TALLER");
            }
        } else {
            throw new BadRequestException("NO EXISTE EL CURSO CON ID " + id);
        }
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CursoFecha> listaPorff(Date fechaInicio) {
        return cursoRepository.findByFechaInicio(fechaInicio);
    }

    public void deleteById(Long id) {
        Optional<Curso> optional = cursoRepository.findById(id);
        if (optional.isPresent()) {
            cursoRepository.deleteById(id);
        } else {
            throw new BadRequestException("El curso con el id " + id + ", no existe");
        }
    }


    @Transactional
    public boolean agregarClientesalCurso(Long idCliente, Long idCurso) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            Optional<Curso> curso = cursoRepository.findById(idCurso);
            if (curso.isPresent()) {
                Cliente cliid = curso.get().getClientes()
                        .stream()
                        .filter(a -> Objects.equals(a.getId(), idCliente))
                        .findFirst().orElse(null);
                if (cliid==null){
                    BigInteger numerodeparticipantes;
                    numerodeparticipantes = count1(idCurso);
                    System.out.println(numerodeparticipantes + " participantes");
                    if (curso.get().getNumParticipantes() >= numerodeparticipantes.longValue() + 1) {
                        try {
                            cliente.get().getCursos().add(curso.get());
                            clienteRepository.save(cliente.get());
                            return true;
                        } catch (Exception e) {
                            throw new BadRequestException("no cumple el numero maximo de participantes");
                        }
                    } else {
                        throw new BadRequestException("SE ALCANZO EL NUMERO MAXIMO DE " + curso.get().getNumParticipantes() + " PARTICIPANTES");
                    }
                } else {
                    throw new BadRequestException("El CLIENTE CON CEDULA " +cliente.get().getPersona().getCedula()+ " ya esta registrado en el curso "+ curso.get().getCursoTaller().getNombre());
                }
            } else {
                throw new BadRequestException("NO EXISTE EL CURSO CON ID: " + idCurso);
            }
        } else {
            throw new BadRequestException("NO EXISTE EL CLIENTE CON EL ID: " + idCliente);
        }
    }


    @PersistenceContext
    private EntityManager entityManager;


    public BigInteger count1(Long id) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM cursos_clientes cc where cc.curso_id= ?");
        nativeQuery.setParameter(1, id);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public N_clientesResponse contar(Long idCurso) {
        Optional<Curso> curso = cursoRepository.findById(idCurso);
        if (curso.isPresent()) {
            N_clientesResponse n = new N_clientesResponse();
            BigInteger numerodeparticipantes;
            numerodeparticipantes =count1(idCurso);
            n.setNumero(numerodeparticipantes.longValue());
            return n;
        }
        else{
            throw new BadRequestException("NO EXISTEN AUN PARTICIPANTES EN EL CURSO: " + idCurso);
        }
    }


    public List<CursoTaller> listarByfechamaxima() {

        LocalDateTime fechaactual = fechaService.obtenerFechaactual().getFechaactual();
        System.out.println("ffffffffffffffffffffff" + fechaactual);
        Query nativeQuery = entityManager.createNativeQuery("SELECT cc.id,ct.nombre,ct.lugar,ct.descripcion,ct.responsable,ct.fecha_inicio, ct.fecha_fin,ct.fecha_maxinscripcion FROM curso_taller ct join curso cc on cc.curso_taller_id = ct.id where ct.fecha_maxinscripcion >= ? ", CursoTaller.class);
        nativeQuery.setParameter(1, fechaactual);
        return nativeQuery.getResultList();
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Cliente_cursoResponse listarcursobyIdCursoutu(Long id) {
        Cliente_cursoResponse cr = new Cliente_cursoResponse();
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            Optional<CursoTaller> cursotaller = cursotallerRepository.findById(curso.get().getCursoTaller().getId());
            if (cursotaller.isPresent()) {
                cr.setId(cursotaller.get().getId());
                cr.setNombre(cursotaller.get().getNombre());
                cr.setLugar(cursotaller.get().getLugar());
                cr.setDescripcion(cursotaller.get().getDescripcion());
                cr.setResponsable(cursotaller.get().getResponsable());
                cr.setFechaInicio(cursotaller.get().getFechaInicio());
                cr.setFechaMaxInscripcion(cursotaller.get().getFechaMaxInscripcion());
                cr.setFechaFin(cursotaller.get().getFechaFin());
                cr.setIdCurso(curso.get().getId());
                cr.setActividades(curso.get().getActividades());
                cr.setMateriales(curso.get().getMateriales());
                cr.setNumParticipantes(curso.get().getNumParticipantes());

                List<ListaClientesRequest> list = curso.get().getClientes().stream().map(ac -> {
                    ListaClientesRequest request = new ListaClientesRequest();
                    request.setCedula(ac.getPersona().getCedula());
                    request.setNombres(ac.getPersona().getNombres());
                    request.setApellidos(ac.getPersona().getApellidos());
                    request.setTelefono(ac.getPersona().getTelefono());
                    request.setGenero(ac.getGenero());
                    request.setEstadoCivil(ac.getEstadoCivil());
                    request.setId(ac.getId());
                    return request;
                }).collect(Collectors.toList());

                cr.setListaClientesRequests(list);

                return cr;
            } else {
                throw new BadRequestException("NO EXISTE UN CURSO ASOCIADO A LA TABLA CURSO/TALLER");
            }
        } else {
            throw new BadRequestException("NO EXISTE EL CURSO CON ID " + id);
        }
    }

    @Transactional
    @Modifying
    public void deleteClientebyIdCurso(Long idCurso, Long idCliente) {
            Optional<Curso> optionalc = cursoRepository.findById(idCurso);
        if (optionalc.isPresent()) {
            Cliente cliid = optionalc.get().getClientes()
                    .stream()
                    .filter(a -> Objects.equals(a.getId(), idCliente))
                    .findFirst()
                    .orElseThrow(() -> new ResponseNotFoundException("Cliente", "id", idCliente + ""));
            try {
                cursoRepository.deleteQuery(idCurso,cliid.getId());
                return;
            } catch (Exception e) {
                throw new BadRequestException("Error al eliminar el cliente con id: ");
            }
        }
        throw new ResponseNotFoundException("Curso", "id", idCurso + "");
    }


    public BigInteger contarporgeneroM(Long id) {
        String generoM="MASCULINO";
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM cursos_clientes cc " +
                "join cliente cl on cl.id=cc.cliente_id where cc.curso_id= ? and cl.genero= ?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, generoM);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarporgeneroF(Long id) {
        String generoF="FEMENINO";
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM cursos_clientes cc " +
                "join cliente cl on cl.id=cc.cliente_id where cc.curso_id= ? and cl.genero= ?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, generoF);
        return (BigInteger) nativeQuery.getSingleResult();
    }
    public BigInteger contarporgeneroOtro(Long id) {
        String generoOtro="OTROS";
        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM cursos_clientes cc " +
                "join cliente cl on cl.id=cc.cliente_id where cc.curso_id= ? and cl.genero= ?");
        nativeQuery.setParameter(1, id);
        nativeQuery.setParameter(2, generoOtro);
        return (BigInteger) nativeQuery.getSingleResult();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CursoporgeneroResponse reporteporgenero(Long idCurso) {
        Optional<Curso> curso = cursoRepository.findById(idCurso);
        if (curso.isPresent()) {
            CursoporgeneroResponse cr = new CursoporgeneroResponse();
            BigInteger numeroM,numeroF, numeroOtro, total;
            Double porcentajeM,porcentajeF,porcentajeOtro;
                numeroM =contarporgeneroM(idCurso);
                numeroF=contarporgeneroF(idCurso);
                numeroOtro=contarporgeneroOtro(idCurso);
                total=count1(idCurso);
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
                    throw new BadRequestException("NO EXISTEN AUN PARTICIPANTES EN EL CURSO: " + idCurso);
                }
            } catch (Exception e) {
                throw new BadRequestException("El curso  "+idCurso+" aun no tiene participantes");
            }
        }
        throw new ResponseNotFoundException("Curso", "id", idCurso + "");
    }


}