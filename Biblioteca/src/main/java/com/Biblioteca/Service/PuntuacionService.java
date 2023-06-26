package com.Biblioteca.Service;

import com.Biblioteca.DTO.Puntuacion.PuntuacionCompleta;
import com.Biblioteca.DTO.Puntuacion.PuntuacionRequest;
import com.Biblioteca.DTO.Puntuacion.PuntuacionResponse;
import com.Biblioteca.Models.Juegos.Juego;
import com.Biblioteca.Models.Juegos.Puntuacion;
import com.Biblioteca.Models.Persona.Usuario;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import com.Biblioteca.Repository.Persona.UsuarioRepository;
import com.Biblioteca.Repository.Puntuacion.JuegoRepository;
import com.Biblioteca.Repository.Puntuacion.PuntuacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PuntuacionService {

    @Autowired
    private PuntuacionRepository puntuacionRepository;

    @Autowired
    private JuegoRepository juegoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaService personaService;


    @Transactional
    public PuntuacionResponse registrarPuntuacion(PuntuacionRequest puntuacionRequest) throws Exception {
        System.out.println(puntuacionRequest);
        metodoprueba();

            System.out.println("entro aqui al else para crear");
            Puntuacion puntuacion=new Puntuacion();
            puntuacion.setPuntuacion(puntuacionRequest.getPuntuacion());
            puntuacion.setIntento(getIntentos(puntuacionRequest.getIntento()));
            puntuacion.setJuego(obtjuego(puntuacionRequest.getIdJuego()));
            puntuacion.setUsuario(obtusuario(puntuacionRequest.getIdUsuario()));
            System.out.println(puntuacion);
            Optional<Juego> juego=juegoRepository.findById(puntuacionRequest.getIdJuego());
            Optional<Usuario> usuario=usuarioRepository.findById(puntuacionRequest.getIdUsuario());
            puntuacionRepository.save(puntuacion);
            return new PuntuacionResponse(puntuacion.getId(),
                    puntuacion.getPuntuacion(),
                    puntuacion.getIntento(),
                    juego.get().getId(),
                    usuario.get().getId());

        }








    public List<Puntuacion> obtenerTodasLasPuntuaciones() {
        return puntuacionRepository.findAll();
    }



    public void metodoprueba(){
        System.out.println(puntuacionRepository.findById(Long.parseLong("2")).toString());
    }

    private Optional<Puntuacion> getRegistro(Long IdJuego,Long IdUsuario) {
        juegoRepository.findById(IdJuego);
        usuarioRepository.findById(IdUsuario);
        return puntuacionRepository.findByJuegoAndUsuario(juegoRepository.findById(IdJuego).get(),usuarioRepository.findById(IdUsuario).get());
    }

    private Long getIntentos(Long intentos){
        if(intentos==Long.parseLong("0")){
        return Long.parseLong("1");
        }else{
            return intentos+1;
        }
    }

    @Transactional
    public PuntuacionCompleta obtDatosCompletos(PuntuacionResponse puntuacionRequesta){
        PuntuacionCompleta pc=new PuntuacionCompleta();
        pc.setPuntuacion(puntuacionRequesta.getPuntuacion());
        pc.setIntento(puntuacionRequesta.getIntento());
        pc.setIdJuego(puntuacionRequesta.getIdJuego());
        pc.setIdUsuario(puntuacionRequesta.getIdUsuario());
        System.out.println(puntuacionRequesta);
        for (int i = 0; i < personaService.listAllUsuarios().size(); i++) {
            if(puntuacionRequesta.getIdUsuario()==personaService.listAllUsuarios().get(i).getIdUsuario()){
                pc.setNombreUsuario(personaService.listAllUsuarios().get(i).getNombres());
                break;
            }
        }
        pc.setNombreJuego(obtjuego(puntuacionRequesta.getIdJuego()).getNombrejuego());
        return pc;

    }

//    @Transactional
//    public PuntuacionCompleta obtDatosCompletos(PuntuacionResponse puntuacionRequesta) {
//        PuntuacionCompleta pc = new PuntuacionCompleta();
//        List<Puntuacion> intentosPuntuacion = new ArrayList<>();
//
//        // Obtén todas las puntuaciones para el usuario y juego especificados
//        List<Puntuacion> puntuaciones = puntuacionRepository.findByUsuarioAndJuego(
//                obtusuario(puntuacionRequesta.getIdUsuario()),
//                obtjuego(puntuacionRequesta.getIdJuego()));
//
//        // Agrúpalas en objetos IntentoPuntuacion y añádelas a la lista
//        for (Puntuacion puntuacion : puntuaciones) {
//            Puntuacion intentoPuntuacion = new Puntuacion();
//            intentoPuntuacion.setIntento(puntuacion.getIntento());
//            intentoPuntuacion.setPuntuacion(puntuacion.getPuntuacion());
//            intentosPuntuacion.add(intentoPuntuacion);
//        }
//
//        pc.setNombreUsuario(puntuacionRequesta.getNombreUsuario());
//        pc.setNombreJuego(puntuacionRequesta.getNombreJuego());
//        pc.setPuntuaciones(intentosPuntuacion);  // establece la lista de puntuaciones
//
//        return pc;
//    }




    private Juego obtjuego(Long idjuego) {
        return juegoRepository.findById(idjuego).get();
    }

    private Usuario obtusuario(Long idusuario){
        return usuarioRepository.findById(idusuario).get();
    }

}
