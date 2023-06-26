package com.Biblioteca.Controller.ServiciosVarios;

import com.Biblioteca.DTO.Servicios.ServiciosVarios.Clientes.ServicioVariosClienteRequest;
import com.Biblioteca.DTO.Servicios.ServiciosVarios.ServiciosVariosRequest;
import com.Biblioteca.DTO.Servicios.ServiciosVarios.ServiciosVariosResponse;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Repository.ServiciosVarios.DatosServicioVarioCliente;
import com.Biblioteca.Service.ServiciosVarios.ServicioVariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/serviciosVarios")
public class ServiciosVariosController {

    @Autowired
    private ServicioVariosService servicioVariosService;

    @PostMapping("/create/serviciovarios")
    public ResponseEntity<?> create(@RequestBody ServiciosVariosRequest request){
        servicioVariosService.registroServiciosVarios(request);
        return new ResponseEntity(new Mensaje("Servicio Varios Creado"), HttpStatus.CREATED);
    }

    @PutMapping("/update/serviciovarios")
    public ResponseEntity<?> update(@RequestBody ServiciosVariosRequest request){
        servicioVariosService.updateServicioVarios(request);
        return new ResponseEntity(new Mensaje("Servicio Varios Actualizado"), HttpStatus.OK);
    }

    @GetMapping("/all/serviciovarios")
    public ResponseEntity<List<ServiciosVariosResponse>>listServiciosVarios(){
        List<ServiciosVariosResponse> varios =servicioVariosService.listAllServiciosVarios();
        return new ResponseEntity<List<ServiciosVariosResponse>>(varios,HttpStatus.OK);
    }

    @PostMapping("/registroServicioCliente")
    public ResponseEntity<?> create(@RequestBody ServicioVariosClienteRequest request){
        servicioVariosService.registroServicioVarioCliente(request);
        return new ResponseEntity(new Mensaje("Servicio Varios-Cliente Creado"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizarServicioCliente")
    public ResponseEntity<?> actualizar(@RequestBody ServicioVariosClienteRequest request){
        return new ResponseEntity<>(servicioVariosService.updateServicioVarioCliente(request), HttpStatus.OK);
    }

    @GetMapping("/listAllServicioCliente/{mes}/{anio}")

    public ResponseEntity<List<DatosServicioVarioCliente>> listAllClientesMesAndAnioServiciosVarios(@PathVariable Long mes, @PathVariable Long  anio){
        List<DatosServicioVarioCliente> lista = servicioVariosService.listaServicioVariosCiente(mes, anio);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
