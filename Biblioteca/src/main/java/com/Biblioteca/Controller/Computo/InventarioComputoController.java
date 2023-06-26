package com.Biblioteca.Controller.Computo;

import com.Biblioteca.DTO.Computo.*;
import com.Biblioteca.DTO.CursoTaller.CursoResponse;
import com.Biblioteca.DTO.Persona.PersonaUsuarioRequest;
import com.Biblioteca.DTO.Ubicacion.BarrioRequest;
import com.Biblioteca.DTO.Ubicacion.BarrioResponse;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Models.Servicio.CentroComputo.ComputoCliente;
import com.Biblioteca.Service.Computo.ComputoClienteService;
import com.Biblioteca.Service.Computo.ComputoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/inventario/computo")
public class InventarioComputoController {

    @Autowired
    private ComputoService service;

    @Autowired
    private ComputoClienteService computoClienteService;

    @PostMapping("/registrocomputador")
    public ResponseEntity<?> registroComputador(@RequestBody InventarioRequest request){
        return new ResponseEntity<>(service.registrarInventario(request), HttpStatus.OK);
    }

    @PutMapping("/actualizarcomputador")
    public ResponseEntity<?> actualizarComputador(@RequestBody InventarioRequest request){
        return new ResponseEntity<>(service.actualizarInventario(request), HttpStatus.OK);
    }

    @PutMapping("/actualizarcomputador/estado/prestamo")
    public ResponseEntity<?> actualizarComputadorEstadoPrestamo(@RequestBody InventarioRequest request){
        return new ResponseEntity<>(service.actualizarInventarioEstadoPrestamo(request), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<InventarioResponse>> all(){
        List<InventarioResponse> allInventario = service.listAllInventario();
        return new ResponseEntity<>(allInventario, HttpStatus.OK);
    }

    @GetMapping("/all/estado/{estado}")
    public ResponseEntity<List<InventarioResponse>> allByEstado(@PathVariable Boolean estado){
        List<InventarioResponse> allInventario = service.listAllInventarioByEstado(estado);
        return new ResponseEntity<>(allInventario, HttpStatus.OK);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<InventarioResponse> inventarioById(@PathVariable Long id){
        InventarioResponse response = service.inventarioById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<InventarioResponse> inventarioByNUmero(@PathVariable Long numero){
        InventarioResponse response = service.inventarioByNumero(numero);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/registrocomputadorcliente")
    public ResponseEntity<?> registroComputadorCliente(@RequestBody ComputoClienteRequest request){
        return new ResponseEntity<>(computoClienteService.registroComputoCliente(request), HttpStatus.OK);
    }

    @GetMapping("/allClienteComputo")
    public ResponseEntity<List<ComputoClienteResponse>> listAllComputoCliente(){
        List<ComputoClienteResponse> cc = computoClienteService.listAllPrestamos();
        return new ResponseEntity<List<ComputoClienteResponse>>(cc, HttpStatus.OK);
    }

    @GetMapping("/allClienteComputo/horaNull")
    public ResponseEntity<List<ComputoClienteResponse>> listAllComputoClienteHoraFinNull(){
        List<ComputoClienteResponse> cc = computoClienteService.listAllPrestamosHoraFin();
        return new ResponseEntity<List<ComputoClienteResponse>>(cc, HttpStatus.OK);
    }

    @GetMapping("/id/horaFin/{id}")
    public ResponseEntity<ComputoClienteHoraFinResponse> computoClienteByIdHoraFin(@PathVariable Long id){
        ComputoClienteHoraFinResponse response = computoClienteService.listHoraFin(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/updatePrestamo")
    public ResponseEntity<?> updatePrestamo(@RequestBody ComputoClienteHoraFinRequest request) {
        computoClienteService.updateComputoCliente(request);
        return new ResponseEntity(new Mensaje("Prestamo Actualizado"), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePrestamoById(@PathVariable Long id){
        computoClienteService.deleteById(id);
        return  new ResponseEntity<>(new Mensaje("Prestamo  eliminado"),HttpStatus.OK);

    }
}
