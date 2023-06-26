package com.Biblioteca.Controller.CopiasImpresiones;


import com.Biblioteca.DTO.CursoTaller.CursoRequest;
import com.Biblioteca.DTO.CursoTaller.CursoResponse;
import com.Biblioteca.DTO.CursoTaller.reportes.CursoporgeneroResponse;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteRequest;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.Clientes.CopiasClienteResponse;
import com.Biblioteca.DTO.Servicios.CopiasImpresiones.reporte.CopiasClientesporGenero;
import com.Biblioteca.DTO.Servicios.PrestamoLibros.Clientes.LibrosClientesRequest;
import com.Biblioteca.Exceptions.Mensaje;
import com.Biblioteca.Service.Copias.CopiasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/copias")
public class CopiasImpresionesController {

    @Autowired
    private CopiasService copiasService;

    @PostMapping("/registrocopias")
    public ResponseEntity<CopiasClienteResponse> create(@RequestBody CopiasClienteRequest request){
        CopiasClienteResponse copias= copiasService.registroCopiasCliente(request);
        return new ResponseEntity<>(copias, HttpStatus.CREATED);
    }


    @PutMapping("/updateregistrodecopias")
    public ResponseEntity<?> actualizarcopias(@RequestBody CopiasClienteRequest cRequest){
        copiasService.actualizarcopias(cRequest);
        return new ResponseEntity(new Mensaje("Registro Actualizado.."), HttpStatus.OK);
    }

    @GetMapping("/allCopias")
    public ResponseEntity<List<CopiasClienteResponse>> listAllcopias(){
        List<CopiasClienteResponse> co = copiasService.listAllCopias();
        return new ResponseEntity<List<CopiasClienteResponse>>(co, HttpStatus.OK);
    }
    @GetMapping("/listbycopiacli/{id}")
    public ResponseEntity<CopiasClienteResponse> listCopiaclientebyId(@PathVariable Long id){
        CopiasClienteResponse co= copiasService.listarbyIdCopiacliente(id);
        return new ResponseEntity<>(co, HttpStatus.OK);
    }

    @GetMapping("/allCopiasbymesandanio/{mes}/{anio}")
    public ResponseEntity<List<CopiasClienteResponse>> listAllcopiasbymesadnanio(@PathVariable Long mes, Long anio){
        List<CopiasClienteResponse> co = copiasService.listarbymesandanio(mes, anio);
        return new ResponseEntity<List<CopiasClienteResponse>>(co, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCopiascliente(@PathVariable Long id){
        copiasService.deleteById(id);
        return  new ResponseEntity<>(new Mensaje("Registro eliminado"),HttpStatus.OK);
    }

    @GetMapping("/reportecursoporgenero/{mes}/{anio}")
    public ResponseEntity<CopiasClientesporGenero> reportegenerocopias(@PathVariable Long mes,@PathVariable Long anio) {
        CopiasClientesporGenero cg= new CopiasClientesporGenero();
        cg=copiasService.reporteporgenero(mes,anio);
        return new ResponseEntity<>(cg, HttpStatus.OK);
    }

}
