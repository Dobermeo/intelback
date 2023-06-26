package com.Biblioteca.Service;

import com.Biblioteca.DTO.Reporte.Reportesd;
import com.Biblioteca.Repository.Reporte.*;
import com.Biblioteca.Repository.Reporte.consultas1.DatosSoloComputoRepository;
import com.Biblioteca.Repository.Reporte.consultas1.DatosSoloCopiasRepository;
import com.Biblioteca.Repository.Reporte.consultas1.DatosSoloLibrosRepository;
import com.Biblioteca.Repository.Reporte.consultas1.DatosSoloVarios;
import com.Biblioteca.Repository.Reporte.consultas2.*;
import com.Biblioteca.Repository.Reporte.consultas3.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private ReportecomputoRepository reportecomputoRepository;
    @Autowired
    private ReporteLibroRepository reportelibroRepository;
    @Autowired
    private ReporteporTallerRepository reporteporTallerRepository;
    @Autowired
    private DatosSoloCopiasRepository datosSoloCopiasRepository;
    @Autowired
    private DatosSoloComputoRepository datosSoloComputoRepository;
    @Autowired
    private DatosSoloVarios datosSoloVariosRepository;
    @Autowired
    private DatosSoloLibrosRepository datosSoloLibrosRepository;

    @Autowired
    private DatosdeTodosRepository datosdeTodosRepository;
    @Autowired
    private DatosTresLVC datosTresLVC;
    @Autowired
    private DatosTresVCCopia datosTresVCCopia;
    @Autowired
    private DatosTresLVCopia datosTresLVCopia;
    @Autowired
    private DatosTresLCCopia datosTresLCCopia;
    @Autowired
    private DatosDosLV datosDosLV;
    @Autowired
    private DatosDosLC datosDosLC;
    @Autowired
    private DatosDosLCopia datosDosLCopia;
    @Autowired
    private DatosDosVC datosDosVC;
    @Autowired
    private DatosDosVCopia datosDosVCopia;
    @Autowired
    private DatosDosCCopia datosDosCCopia;
    @Transactional
    public List<Reportesd> listarbycopias(Long mes, Long anio) {
        List<DatosReporte> datos1=reporteRepository.findAllByMesandAnio(mes, anio);
        return datos1.stream().map(clienteRequest -> {
            Reportesd listaa=new Reportesd();
            listaa.setCodigo("B.M. EL VALLE");
            listaa.setCedula(clienteRequest.getCedula());
            listaa.setNombres(clienteRequest.getNombres());
            listaa.setApellidos(clienteRequest.getApellidos());
            listaa.setGenero(clienteRequest.getGenero());
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listaa.setFecha(clienteRequest.getAnio()+"-"+fmt.format("%02d",clienteRequest.getMes())+"-"+fmt2.format("%02d",clienteRequest.getDia()));
            listaa.setEmail(clienteRequest.getEmail());
            listaa.setTelefono(clienteRequest.getTelefono());
            listaa.setDiscapacidad(clienteRequest.getDiscapacidad());
            listaa.setFecha_nacimiento(clienteRequest.getFecha_nacimiento());
            listaa.setEdad(clienteRequest.getEdad());
            listaa.setProvincia(clienteRequest.getProvincia());
            listaa.setCanton(clienteRequest.getCanton());
            listaa.setParroquia(clienteRequest.getParroquia());
            listaa.setBarrio(clienteRequest.getBarrio());
            listaa.setEstado_civil(clienteRequest.getEstado_civil());
            listaa.setVerificables("Incluido en el informe mensual");
            listaa.setCopias(Long.valueOf(1));
            return listaa;
        }).collect(Collectors.toList());
    }


    @Transactional
    public List<Reportesd> listarbycomputo(Long mes, Long anio) {
        List<DatosReporte> datos2 = reportecomputoRepository.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio()+"-"+fmt.format("%02d",clienteRequest2.getMes())+"-"+fmt2.format("%02d",clienteRequest2.getDia()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setComputo(Long.valueOf(1));
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            return listab;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd> listarbyprestamolibros(Long mes, Long anio) {
        List<DatosReporte> datos3 = reportelibroRepository.findAllByMesandAnio(mes, anio);
        return datos3.stream().map(clienteRequest3 -> {
            Reportesd listac = new Reportesd();
            listac.setCodigo("B.M. EL VALLE");
            listac.setCedula(clienteRequest3.getCedula());
            listac.setNombres(clienteRequest3.getNombres());
            listac.setApellidos(clienteRequest3.getApellidos());
            listac.setGenero(clienteRequest3.getGenero());
            listac.setEmail(clienteRequest3.getEmail());
            listac.setTelefono(clienteRequest3.getTelefono());
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listac.setFecha(clienteRequest3.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest3.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest3.getDia_prestamo()));

            listac.setDiscapacidad(clienteRequest3.getDiscapacidad());
            listac.setFecha_nacimiento(clienteRequest3.getFecha_nacimiento());
            listac.setEdad(clienteRequest3.getEdad());
            listac.setProvincia(clienteRequest3.getProvincia());
            listac.setCanton(clienteRequest3.getCanton());
            listac.setParroquia(clienteRequest3.getParroquia());
            listac.setBarrio(clienteRequest3.getBarrio());
            listac.setEstado_civil(clienteRequest3.getEstado_civil());
            listac.setVerificables("Incluido en el informe mensual");

            listac.setRepositorio(Long.valueOf(1));
            return listac;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd>listartallerbyClientes(Long mes, Long anio) {

        Formatter fmt = new Formatter();
        fmt.format("%02d",mes);
        String fec=anio+"-"+fmt;
        List<DatosReporte> datos4 = reporteporTallerRepository.findAllByFecha_inicio(fec);
        System.out.println(fec);
        return datos4.stream().map(clienteRequest4 -> {
            Reportesd listad = new Reportesd();
            listad.setCodigo("B.M. EL VALLE");
            listad.setNombretaller(clienteRequest4.getNombre());
            listad.setTalleres(Long.valueOf(1));
            listad.setFecha(clienteRequest4.getFecha_inicio()+" a "+clienteRequest4.getFecha_fin());

            listad.setCedula(clienteRequest4.getCedula());
            listad.setNombres(clienteRequest4.getNombres());
            listad.setApellidos(clienteRequest4.getApellidos());
            listad.setGenero(clienteRequest4.getGenero());
            listad.setEmail(clienteRequest4.getEmail());
            listad.setTelefono(clienteRequest4.getTelefono());
            listad.setDiscapacidad(clienteRequest4.getDiscapacidad());
            listad.setFecha_nacimiento(clienteRequest4.getFecha_nacimiento());
            listad.setEdad(clienteRequest4.getEdad());
            listad.setProvincia(clienteRequest4.getProvincia());
            listad.setCanton(clienteRequest4.getCanton());
            listad.setParroquia(clienteRequest4.getParroquia());
            listad.setBarrio(clienteRequest4.getBarrio());
            listad.setEstado_civil(clienteRequest4.getEstado_civil());
            listad.setVerificables("Incluido en el informe mensual");
            return listad;
        }).collect(Collectors.toList());

    }

////////////////UNO////////////////

    @Transactional
    public List<Reportesd> listarsolocopias(Long mes, Long anio) {
        List<DatosReporte> datos1=datosSoloCopiasRepository.findAllByMesandAnio(mes, anio);
        return datos1.stream().map(clienteRequest -> {
            Reportesd listaa=new Reportesd();
            listaa.setCodigo("B.M. EL VALLE");
            listaa.setCedula(clienteRequest.getCedula());
            listaa.setNombres(clienteRequest.getNombres());
            listaa.setApellidos(clienteRequest.getApellidos());
            listaa.setGenero(clienteRequest.getGenero());
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listaa.setFecha(clienteRequest.getAnio()+"-"+fmt.format("%02d",clienteRequest.getMes())+"-"+fmt2.format("%02d",clienteRequest.getDia()));
            listaa.setEmail(clienteRequest.getEmail());
            listaa.setTelefono(clienteRequest.getTelefono());
            listaa.setDiscapacidad(clienteRequest.getDiscapacidad());
            listaa.setFecha_nacimiento(clienteRequest.getFecha_nacimiento());
            listaa.setEdad(clienteRequest.getEdad());
            listaa.setProvincia(clienteRequest.getProvincia());
            listaa.setCanton(clienteRequest.getCanton());
            listaa.setParroquia(clienteRequest.getParroquia());
            listaa.setBarrio(clienteRequest.getBarrio());
            listaa.setEstado_civil(clienteRequest.getEstado_civil());
            listaa.setVerificables("Incluido en el informe mensual");
            listaa.setCopias(Long.valueOf(1));
            return listaa;
        }).collect(Collectors.toList());
    }
    @Transactional
    public List<Reportesd> listarsolocomputo(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosSoloComputoRepository.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio()+"-"+fmt.format("%02d",clienteRequest2.getMes())+"-"+fmt2.format("%02d",clienteRequest2.getDia()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setComputo(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd> listarsoloserviciosvarios(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosSoloVariosRepository.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setBiblioteca(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }


    @Transactional
    public List<Reportesd> listarsololibros(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosSoloLibrosRepository.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setRepositorio(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }
    @Transactional
    public List<Reportesd> listartodos(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosdeTodosRepository.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest -> {
            Reportesd listaa=new Reportesd();
            listaa.setCodigo("B.M. EL VALLE");
            listaa.setCedula(clienteRequest.getCedula());
            listaa.setNombres(clienteRequest.getNombres());
            listaa.setApellidos(clienteRequest.getApellidos());
            listaa.setGenero(clienteRequest.getGenero());
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listaa.setFecha(clienteRequest.getAnio()+"-"+fmt.format("%02d",clienteRequest.getMes())+"-"+fmt2.format("%02d",clienteRequest.getDia()));
            listaa.setEmail(clienteRequest.getEmail());
            listaa.setTelefono(clienteRequest.getTelefono());
            listaa.setDiscapacidad(clienteRequest.getDiscapacidad());
            listaa.setFecha_nacimiento(clienteRequest.getFecha_nacimiento());
            listaa.setEdad(clienteRequest.getEdad());
            listaa.setProvincia(clienteRequest.getProvincia());
            listaa.setCanton(clienteRequest.getCanton());
            listaa.setParroquia(clienteRequest.getParroquia());
            listaa.setBarrio(clienteRequest.getBarrio());
            listaa.setEstado_civil(clienteRequest.getEstado_civil());
            listaa.setVerificables("Incluido en el informe mensual");
            listaa.setCopias(Long.valueOf(1));
            listaa.setComputo(Long.valueOf(1));
            listaa.setRepositorio(Long.valueOf(1));
            listaa.setBiblioteca(Long.valueOf(1));

            return listaa;
        }).collect(Collectors.toList());
    }

    ///////////////////tres/////////////////
    @Transactional
    public List<Reportesd> listarLVC(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosTresLVC.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setRepositorio(Long.valueOf(1));
            listab.setBiblioteca(Long.valueOf(1));
            listab.setComputo(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd> listarVCCopia(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosTresVCCopia.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setCopias(Long.valueOf(1));
            listab.setBiblioteca(Long.valueOf(1));
            listab.setComputo(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd> listarLVCopia(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosTresLVCopia.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setRepositorio(Long.valueOf(1));
            listab.setBiblioteca(Long.valueOf(1));
            listab.setCopias(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }
    @Transactional
    public List<Reportesd> listarLCCopia(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosTresLCCopia.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setRepositorio(Long.valueOf(1));
            listab.setComputo(Long.valueOf(1));
            listab.setCopias(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }

    /////DOS//
    @Transactional
    public List<Reportesd> listarLV(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosDosLV.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setRepositorio(Long.valueOf(1));
            listab.setBiblioteca(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }
    @Transactional
    public List<Reportesd> listarLC(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosDosLC.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setRepositorio(Long.valueOf(1));
            listab.setComputo(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd> listarLCopia(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosDosLCopia.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setRepositorio(Long.valueOf(1));
            listab.setCopias(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }
    @Transactional
    public List<Reportesd> listarVC(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosDosVC.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setBiblioteca(Long.valueOf(1));
            listab.setComputo(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }
    @Transactional
    public List<Reportesd> listarVCopia(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosDosVCopia.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio_prestamo()+"-"+fmt.format("%02d",clienteRequest2.getMes_prestamo())+"-"+fmt2.format("%02d",clienteRequest2.getDia_prestamo()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setBiblioteca(Long.valueOf(1));
            listab.setCopias(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }


    @Transactional
    public List<Reportesd> listarCCopia(Long mes, Long anio) {
        List<DatosReporte> datos2 = datosDosCCopia.findAllByMesandAnio(mes, anio);
        return datos2.stream().map(clienteRequest2 -> {
            Reportesd listab = new Reportesd();
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            listab.setCodigo("B.M. EL VALLE");
            listab.setFecha(clienteRequest2.getAnio()+"-"+fmt.format("%02d",clienteRequest2.getMes())+"-"+fmt2.format("%02d",clienteRequest2.getDia()));
            listab.setCedula(clienteRequest2.getCedula());
            listab.setNombres(clienteRequest2.getNombres());
            listab.setApellidos(clienteRequest2.getApellidos());
            listab.setGenero(clienteRequest2.getGenero());
            listab.setEmail(clienteRequest2.getEmail());
            listab.setTelefono(clienteRequest2.getTelefono());
            listab.setDiscapacidad(clienteRequest2.getDiscapacidad());
            listab.setFecha_nacimiento(clienteRequest2.getFecha_nacimiento());
            listab.setEdad(clienteRequest2.getEdad());
            listab.setProvincia(clienteRequest2.getProvincia());
            listab.setCanton(clienteRequest2.getCanton());
            listab.setParroquia(clienteRequest2.getParroquia());
            listab.setBarrio(clienteRequest2.getBarrio());
            listab.setEstado_civil(clienteRequest2.getEstado_civil());
            listab.setVerificables("Incluido en el informe mensual");
            listab.setComputo(Long.valueOf(1));
            listab.setCopias(Long.valueOf(1));
            return listab;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Reportesd> listartodo3(Long mes, Long anio) {
        List<Reportesd> list = new ArrayList<>();
        list.addAll((Collection<? extends Reportesd>) listarbycopias(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarbycomputo(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarbyprestamolibros(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listartallerbyClientes(mes, anio));
        Collections.sort(list, new Comparator<Reportesd>() {
            @Override
            public int compare(Reportesd o1, Reportesd o2) {
                return o1.getFecha().compareTo(o2.getFecha());
            }
        });
        return list;
    }
    @Transactional
    public List<Reportesd> retornarlista(Long mes, Long anio) {
        List<Reportesd> list = new ArrayList<>();
        list.addAll((Collection<? extends Reportesd>) listarsolocopias(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarsolocomputo(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarsoloserviciosvarios(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarsololibros(mes, anio));

        list.addAll((Collection<? extends Reportesd>) listartodos(mes, anio));

        list.addAll((Collection<? extends Reportesd>) listarLVC(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarVCCopia(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarLVCopia(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarLCCopia(mes, anio));

        list.addAll((Collection<? extends Reportesd>) listarLV(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarLC(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarLCopia(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarVC(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarVCopia(mes, anio));
        list.addAll((Collection<? extends Reportesd>) listarCCopia(mes, anio));

        list.addAll((Collection<? extends Reportesd>) listartallerbyClientes(mes, anio));
        Collections.sort(list, new Comparator<Reportesd>() {
            @Override
            public int compare(Reportesd o1, Reportesd o2) {
                return o1.getFecha().compareTo(o2.getFecha());
            }
        });
        return list;
    }

}
