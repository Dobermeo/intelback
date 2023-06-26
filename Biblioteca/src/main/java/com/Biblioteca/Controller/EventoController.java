package com.Biblioteca.Controller;

import com.Biblioteca.DTO.Evento.EventoRequest;
import com.Biblioteca.DTO.Evento.EventoResponse;
import com.Biblioteca.DTO.Evento.reporte.EventopormesResponse;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteResponse;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping("/registrarEvento")
    public ResponseEntity<?> registroEvento(@RequestBody EventoRequest eventoRequest){
        eventoService.registroevento(eventoRequest);
        return new ResponseEntity<>(new Mensaje("Evento Creado"), HttpStatus.OK);
    }

    @PutMapping("/updateevento")
    public ResponseEntity<?> updateEvento(@RequestBody EventoRequest eventoRequest){
        eventoService.actualizardatosevento(eventoRequest);
        return new ResponseEntity(new Mensaje("Evento Actualizado"), HttpStatus.OK);
    }
    @PutMapping("/agregarnparticipantes")
    public ResponseEntity<?> agregarn_participantes(@RequestBody EventoRequest eventoRequest){
        eventoService.insertarnumeroparticipantes(eventoRequest);
        return new ResponseEntity(new Mensaje("Numero estimado de participantes agregado"), HttpStatus.OK);
    }

    @GetMapping("/allEventos")
    public ResponseEntity<List<EventoResponse>> listAllEventos(){
        List<EventoResponse> ev = eventoService.listAllEventos();
        return new ResponseEntity<List<EventoResponse>>(ev, HttpStatus.OK);
    }

    @GetMapping("/listeventossinparticipantes")
    public ResponseEntity<List<EventoResponse>> listAllEventossinparticipantes(){
        List<EventoResponse> ev = eventoService.listAllEventossinparticipantes();
        return new ResponseEntity<List<EventoResponse>>(ev, HttpStatus.OK);
    }
    @GetMapping("/listeventosconparticipantes")
    public ResponseEntity<List<EventoResponse>> listAllEventosconparticipantes(){
        List<EventoResponse> ev = eventoService.listAllEventosconparticipantes();
        return new ResponseEntity<List<EventoResponse>>(ev, HttpStatus.OK);
    }

    @DeleteMapping("eliminarevento/{id}")
    public ResponseEntity<?> deleteEvento(@PathVariable Long id){
        eventoService.deleteById(id);
        return  new ResponseEntity<>(new Mensaje("Evento eliminado"),HttpStatus.OK);
    }

    @GetMapping("/eventospormes/{mes}/{anio}")
    public ResponseEntity<List<EventopormesResponse>> eventospormer(@PathVariable Long mes, @PathVariable Long anio){
        List<EventopormesResponse> co = eventoService.reporteeventopormesyanio(mes, anio);
        return new ResponseEntity<List<EventopormesResponse>>(co, HttpStatus.OK);
    }
}
