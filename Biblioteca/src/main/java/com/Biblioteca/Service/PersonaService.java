package com.Biblioteca.Service;

import com.Biblioteca.DTO.Persona.*;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Persona.Persona;
import com.Biblioteca.Models.Persona.Usuario;
import com.Biblioteca.Models.Roles.Roles;
import com.Biblioteca.Models.Ubicacion.*;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import com.Biblioteca.Repository.Persona.UsuarioRepository;
import com.Biblioteca.Repository.RolesRepository;
import com.Biblioteca.Repository.Ubicacion.*;
import com.Biblioteca.Security.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonaService implements UserDetailsService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CantonRepository cantonRepository;

    @Autowired
    private ParroquiaRepository parroquiaRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private BarrioRepository barrioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public PersonaClienteResponse registrarCliente(PersonaClienteRequest personaClienteRequest){
        Optional<Persona> optionalPersona = personaRepository.findByCedula(personaClienteRequest.getCedula());
        if(!optionalPersona.isPresent()){
            Optional<Persona> optionalPersonaEmail = personaRepository.findByEmail(personaClienteRequest.getEmail());
            if(!optionalPersonaEmail.isPresent()){
                Persona newPersona = new Persona();
                newPersona.setCedula(personaClienteRequest.getCedula());
                newPersona.setApellidos(personaClienteRequest.getApellidos());
                newPersona.setNombres(personaClienteRequest.getNombres());
                newPersona.setEmail(personaClienteRequest.getEmail());
                newPersona.setTelefono(personaClienteRequest.getTelefono());
                Persona persona = personaRepository.save(newPersona);
                if(persona!=null){
                    guardarCliente(persona.getCedula(), personaClienteRequest.getFechaNacimiento(), personaClienteRequest.getEstadoCivil(),
                            personaClienteRequest.getGenero(), personaClienteRequest.getDiscapacidad(), personaClienteRequest.getIdBarrio(),
                            personaClienteRequest.getIdCanton(), personaClienteRequest.getIdParroquia(), personaClienteRequest.getIdProvincia(),
                            personaClienteRequest.getNombreResponsable(), personaClienteRequest.getTelefonoResponsbale());
                    Optional<Cliente> cliente = clienteRepository.findByPersona(persona);
                    Optional<Canton> canton = cantonRepository.findById(personaClienteRequest.getIdCanton());
                    Optional<Parroquia> parroquia = parroquiaRepository.findById(personaClienteRequest.getIdParroquia());
                    Optional<Provincia> provincia = provinciaRepository.findById(personaClienteRequest.getIdProvincia());
                    Optional<Barrio> barrio = barrioRepository.findById(personaClienteRequest.getIdBarrio());
                    return new PersonaClienteResponse(persona.getId(),cliente.get().getId(),persona.getCedula(),
                            persona.getApellidos(), persona.getNombres(),cliente.get().getFechaNacimiento(),
                            cliente.get().getEdad(),cliente.get().getGenero(), persona.getTelefono(), persona.getEmail(), cliente.get().getEstadoCivil(), cliente.get().getDiscapacidad(),cliente.get().getNombreResponsable(), cliente.get().getTelefonoResponsbale(),
                            barrio.get().getId(), barrio.get().getBarrio(), parroquia.get().getId(),parroquia.get().getParroquia(), canton.get().getId(),canton.get().getCanton(), provincia.get().getId(),provincia.get().getProvincia());
                }else {

                    log.error("No se puedo guardar el cliente con cédula: {} y email: {}", personaClienteRequest.getCedula(), personaClienteRequest.getEmail());
                    throw new BadRequestException("No se pudo guardar el cliente");
                }
            }else {
                throw new BadRequestException("El email ingresado, ya esta registrado");
            }
        }else {
            throw new BadRequestException("La cédula ingresada, ya está registrada");
        }
    }





    private boolean guardarCliente (String cedula, Date fechaNa, String estado, String genero, Boolean discapacidad, Long idBarrio, Long idCanton, Long idParroquia, Long idProvincia, String nombreResponsable, String telefonoResponsable ){
        Optional<Persona> optionalPersona = personaRepository.findByCedula(cedula);
        if(optionalPersona.isPresent()){
            Persona persona = optionalPersona.get();
            Cliente newCliente = new Cliente();

            newCliente.setFechaNacimiento(fechaNa);
            newCliente.setEdad(edad(fechaNa));
            newCliente.setEstadoCivil(estado);
            newCliente.setGenero(genero);
            newCliente.setDiscapacidad(discapacidad);
            newCliente.setUbicacion(guardarUbicacion(idBarrio, idCanton, idParroquia, idProvincia));
            newCliente.setNombreResponsable(nombreResponsable);
            newCliente.setTelefonoResponsbale(telefonoResponsable);
            newCliente.setPersona(persona);
            Cliente cliente = clienteRepository.save(newCliente);
            if(cliente!=null){
                return true;
            }else{
                throw new BadRequestException("Cliente no registrado");

            }
        }else{
            throw new BadRequestException("La cedula ingresada, no está registrada");

        }

    }

    public boolean updateCliente(PersonaClienteRequest personaClienteRequest){
        Optional<Persona> optionalPersona = personaRepository.findById(personaClienteRequest.getId());
        if(optionalPersona.isPresent()) {

            optionalPersona.get().setCedula(personaClienteRequest.getCedula());
            optionalPersona.get().setApellidos(personaClienteRequest.getApellidos());
            optionalPersona.get().setNombres(personaClienteRequest.getNombres());
            optionalPersona.get().setTelefono(personaClienteRequest.getTelefono());
            optionalPersona.get().setEmail( personaClienteRequest.getEmail());
            try{
                Persona persona = personaRepository.save(optionalPersona.get());
                if(persona != null){
                    actualizarCliente(persona, personaClienteRequest.getFechaNacimiento(), personaClienteRequest.getEstadoCivil(),
                            personaClienteRequest.getGenero(), personaClienteRequest.getDiscapacidad(), personaClienteRequest.getIdBarrio(),
                            personaClienteRequest.getIdCanton(), personaClienteRequest.getIdParroquia(), personaClienteRequest.getIdProvincia(), personaClienteRequest.getNombreResponsable(),
                            personaClienteRequest.getTelefonoResponsbale());

                }else {
                    throw new BadRequestException("No se actualizó la persona");
                }
            }catch (Exception ex) {
                throw new BadRequestException("No se actualizó la persona" + ex);
            }
        }else{
            throw new BadRequestException("No existe una persona con id" + personaClienteRequest.getId());
        }
        return false;
    }

    private boolean actualizarCliente (Persona persona, Date fechaNa, String estado, String genero, Boolean discapacidad, Long idBarrio, Long idCanton, Long idParroquia, Long idProvincia, String nombreResponsable, String telefonoResponsable ){
        Optional<Cliente> optionalCliente = clienteRepository.findByPersona(persona);
        if(optionalCliente.isPresent()){
            optionalCliente.get().setFechaNacimiento(fechaNa);
            optionalCliente.get().setEstadoCivil(estado);
            optionalCliente.get().setEdad(edad(fechaNa));
            optionalCliente.get().setGenero(genero);
            optionalCliente.get().setDiscapacidad(discapacidad);
            optionalCliente.get().setNombreResponsable(nombreResponsable);
            optionalCliente.get().setTelefonoResponsbale(telefonoResponsable);
            optionalCliente.get().setUbicacion(guardarUbicacion(idBarrio, idCanton, idParroquia, idProvincia));
            try{
                Cliente cliente = clienteRepository.save(optionalCliente.get());
                return true;
            }catch (Exception ex) {
                throw new BadRequestException("No se actualizó tbl_cliente" + ex);
            }
        }
        throw new BadRequestException("No existe el cliente");

        }

        public Long edad (Date fechaNacimiento){
        Period edadC = Period.between(LocalDate.of(fechaNacimiento.getYear(),fechaNacimiento.getMonth()+1, fechaNacimiento.getDay()+1), LocalDate.now());
        int años =edadC.getYears()-1900;
        System.out.println("AÑOS"+años);
        return Long.parseLong(años+"");
    }

    private Ubicacion guardarUbicacion(Long idBarrio, Long idCanton, Long idParroquia, Long idProvincia){
        Optional<Barrio> optionalBarrio = barrioRepository.findById(idBarrio);
        if(optionalBarrio.isPresent()){
            Optional<Canton> optionalCanton = cantonRepository.findById(idCanton);
            if(optionalCanton.isPresent()){
                Optional<Parroquia> optionalParroquia = parroquiaRepository.findById(idParroquia);
                if(optionalParroquia.isPresent()){
                    Optional<Provincia> optionalProvincia = provinciaRepository.findById(idProvincia);
                    if (optionalProvincia.isPresent()) {
                        Optional<Ubicacion> optionalUbicacion=  ubicacionRepository.findByBarrioAndCantonAndParroquiaAndProvincia(optionalBarrio.get(),
                                optionalCanton.get(), optionalParroquia.get(), optionalProvincia.get());
                        if (!optionalUbicacion.isPresent() ) {
                            Ubicacion newUbicacion = new Ubicacion();
                            newUbicacion.setBarrio(optionalBarrio.get());
                            newUbicacion.setCanton(optionalCanton.get());
                            newUbicacion.setParroquia(optionalParroquia.get());
                            newUbicacion.setProvincia(optionalProvincia.get());
                            Ubicacion ubicacion = ubicacionRepository.save(newUbicacion);
                            if(ubicacion!=null){
                                return ubicacion;
                            }else{
                                throw new BadRequestException("Ubicacion no registrada");
                            }
                        }else{
                            return optionalUbicacion.get();
                        }

                    }else{
                        throw new BadRequestException("No existe una provincia con id" +idProvincia);
                    }
                }else{
                    throw new BadRequestException("No existe una parroquia con id" +idParroquia);
                }
            }else{
                throw new BadRequestException("No existe un canton con id" +idCanton);
            }
        }else{
            throw new BadRequestException("No existe un barrio con id" +idBarrio);
        }

    }


    @Transactional
    public PersonaUsuarioResponse registrarUsuario(PersonaUsuarioRequest personaUsuarioRequest) throws Exception {
        Optional<Persona> optionalPersona = personaRepository.findByEmail(personaUsuarioRequest.getEmail());
        if(!optionalPersona.isPresent()) {
            Persona newPersona = new Persona();
            newPersona.setCedula(personaUsuarioRequest.getCedula());
            newPersona.setApellidos(personaUsuarioRequest.getApellidos());
            newPersona.setNombres(personaUsuarioRequest.getNombres());
            newPersona.setTelefono(personaUsuarioRequest.getTelefono());
            newPersona.setEmail(personaUsuarioRequest.getEmail());
            if (!getPersona(personaUsuarioRequest.getCedula())) {
                Persona persona = personaRepository.save(newPersona);
                if (persona != null) {
                    guardarUsuario(persona.getCedula(), personaUsuarioRequest.getClave(), personaUsuarioRequest.getIdRol());
                    Optional<Usuario> user = usuarioRepository.findByPersona(persona);
                    return new PersonaUsuarioResponse(persona.getId(),persona.getId(), persona.getCedula(),
                            persona.getApellidos(), persona.getNombres(), persona.getEmail(),
                             persona.getTelefono(), user.get().getClave(), user.get().getRoles().getId(),generateTokenSignUp(personaUsuarioRequest));

                }else {
                    log.error("No se puedo guardar el usuario con cédula: {} e email: {}", personaUsuarioRequest.getCedula(), personaUsuarioRequest.getEmail());
                    throw new BadRequestException("No se pudo guardar el usuario");
                }
            }else {
                log.error("La cédula ya está registrada: {}", personaUsuarioRequest.getCedula());
                throw new BadRequestException("La cedula ingresada, ya esta registrada, si la cedula le pertenece contactenos a");
            }
        }else {
            throw new BadRequestException("El email ingresado, ya esta registrado");
        }
    }


    public boolean updateUsuario(PersonaUsuarioRequest personaUsuarioRequest){
        Optional<Persona> optionalPersona = personaRepository.findById(personaUsuarioRequest.getId());
        if(optionalPersona.isPresent()) {

            optionalPersona.get().setCedula(personaUsuarioRequest.getCedula());
            optionalPersona.get().setApellidos(personaUsuarioRequest.getApellidos());
            optionalPersona.get().setNombres(personaUsuarioRequest.getNombres());
            optionalPersona.get().setTelefono(personaUsuarioRequest.getTelefono());
            optionalPersona.get().setEmail( personaUsuarioRequest.getEmail());
            try{
                Persona persona = personaRepository.save(optionalPersona.get());
                if(persona != null){
                   actualizarUsuario(persona, personaUsuarioRequest.getClave(), personaUsuarioRequest.getIdRol());

                }else {
                    throw new BadRequestException("No se actualizó la persona");
                }
            }catch (Exception ex) {
                throw new BadRequestException("No se actualizó la persona" + ex);
            }
        }else{
            throw new BadRequestException("No existe una persona con id" + personaUsuarioRequest.getId());
        }
        return false;
    }
    private boolean guardarUsuario(String cedula,String clave,Long idRol){
        Optional<Persona> optionalPersona = personaRepository.findByCedula(cedula);
        if(optionalPersona.isPresent()){
            Optional<Roles> optionalRoles= rolesRepository.findById(idRol);
            if(optionalRoles.isPresent()){
            Persona persona = optionalPersona.get();
            Usuario newUsuario = new Usuario();
            newUsuario.setClave(clave);
            newUsuario.setPersona(persona);
            newUsuario.setRoles(optionalRoles.get());
            Usuario user = usuarioRepository.save(newUsuario);
            if(user!=null){
                return true;
            }else{
                throw new BadRequestException("Usuario no registrado");
            }

            }else{
                throw new BadRequestException("El rol seleccionado no existe");
            }

        }else{
            throw new BadRequestException("La cedula ingresada, no está registrada");
        }
    }
    private boolean actualizarUsuario(Persona persona, String clave,Long idRol){
        Optional<Usuario> optionalUsuario = usuarioRepository.findByPersona(persona);
        if(optionalUsuario.isPresent()){
            Optional<Roles> optionalRoles= rolesRepository.findById(idRol);
            if(optionalRoles.isPresent()){

                optionalUsuario.get().setClave(clave);
                optionalUsuario.get().setPersona(persona);
                optionalUsuario.get().setRoles(optionalRoles.get());

                try{
                    Usuario usuario = usuarioRepository.save(optionalUsuario.get());
                    return true;
                }catch (Exception ex) {
                    throw new BadRequestException("No se actualizó tbl_usuario" + ex);
                }
            }else{
                throw new BadRequestException("El rol seleccionado no existe");
            }

        }else{
            throw new BadRequestException("La cedula ingresada, no está registrada");
        }
    }


    private boolean getPersona(String cedula) {
        return personaRepository.existsByCedula(cedula);
    }


    public List<PersonaClienteResponse> listAllClientes(){
        List<Cliente> cliente = clienteRepository.findAll();
        return cliente.stream().map(clienteRequest->{
            PersonaClienteResponse pcr = new PersonaClienteResponse();
            pcr.setId(clienteRequest.getPersona().getId());
            pcr.setIdCliente(clienteRequest.getId());
            pcr.setCedula(clienteRequest.getPersona().getCedula());
            pcr.setNombres(clienteRequest.getPersona().getNombres());
            pcr.setApellidos(clienteRequest.getPersona().getApellidos());
            pcr.setFechaNacimiento(clienteRequest.getFechaNacimiento());
            pcr.setEdad(clienteRequest.getEdad());
            pcr.setGenero(clienteRequest.getGenero());
            pcr.setTelefono(clienteRequest.getPersona().getTelefono());
            pcr.setEmail(clienteRequest.getPersona().getEmail());
            pcr.setEstadoCivil(clienteRequest.getEstadoCivil());
            pcr.setDiscapacidad(clienteRequest.getDiscapacidad());
            pcr.setIdBarrio(clienteRequest.getUbicacion().getBarrio().getId());
            pcr.setBarrio(clienteRequest.getUbicacion().getBarrio().getBarrio());
            pcr.setIdParroquia(clienteRequest.getUbicacion().getParroquia().getId());
            pcr.setParroquia(clienteRequest.getUbicacion().getParroquia().getParroquia());
            pcr.setIdCanton(clienteRequest.getUbicacion().getCanton().getId());
            pcr.setCanton(clienteRequest.getUbicacion().getCanton().getCanton());
            pcr.setIdProvincia(clienteRequest.getUbicacion().getProvincia().getId());
            pcr.setProvincia(clienteRequest.getUbicacion().getProvincia().getProvincia());
            pcr.setNombreResponsable(clienteRequest.getNombreResponsable());
            pcr.setTelefonoResponsbale(clienteRequest.getTelefonoResponsbale());
            return pcr;
        }).collect(Collectors.toList());
    }

    public List<PersonaUsuarioResponse> listAllUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuarioRequest->{
            PersonaUsuarioResponse pcr = new PersonaUsuarioResponse();
            pcr.setId(usuarioRequest.getPersona().getId());
            pcr.setIdUsuario(usuarioRequest.getId());
            pcr.setCedula(usuarioRequest.getPersona().getCedula());
            pcr.setNombres(usuarioRequest.getPersona().getNombres());
            pcr.setApellidos(usuarioRequest.getPersona().getApellidos());
            pcr.setTelefono(usuarioRequest.getPersona().getTelefono());
            pcr.setEmail(usuarioRequest.getPersona().getEmail());
            pcr.setIdRol(usuarioRequest.getRoles().getId());

            return pcr;
        }).collect(Collectors.toList());
    }

    public PersonaUsuarioResponse usuarioByCedula(String cedula){
        PersonaUsuarioResponse response = new PersonaUsuarioResponse();
        Optional<Persona> persona = personaRepository.findByCedula(cedula);
        if(persona.isPresent()) {
            Optional<Usuario> user = usuarioRepository.findByPersona(persona.get());
            if(user.isPresent()) {
                response.setId(persona.get().getId());
                response.setIdUsuario(user.get().getId());
                response.setCedula(user.get().getPersona().getCedula());
                response.setNombres(user.get().getPersona().getNombres());
                response.setApellidos(user.get().getPersona().getApellidos());
                response.setTelefono(user.get().getPersona().getTelefono());
                response.setEmail(user.get().getPersona().getEmail());
                response.setIdRol(user.get().getRoles().getId());
                return response;
            }else{
                throw new BadRequestException("No existe un persona con cédula" +cedula);
            }
        }else{
            throw new BadRequestException("No existe un cliente vinculado a esa persona");
        }
    }


    public PersonaClienteResponse ClienteByCedula(String cedula){
        PersonaClienteResponse response = new PersonaClienteResponse();
        Optional<Persona> persona = personaRepository.findByCedula(cedula);
        if(persona.isPresent()) {
            Optional<Cliente> cliente = clienteRepository.findByPersona(persona.get());
            if(cliente.isPresent()) {
                response.setId(persona.get().getId());
                response.setIdCliente(cliente.get().getId());
                response.setCedula(persona.get().getCedula());
                response.setNombres(persona.get().getNombres());
                response.setApellidos(persona.get().getApellidos());
                response.setFechaNacimiento(cliente.get().getFechaNacimiento());
                response.setEdad(cliente.get().getEdad());
                response.setGenero(cliente.get().getGenero());
                response.setTelefono(persona.get().getTelefono());
                response.setEmail(persona.get().getEmail());
                response.setEstadoCivil(cliente.get().getEstadoCivil());
                response.setDiscapacidad(cliente.get().getDiscapacidad());
                response.setIdBarrio(cliente.get().getUbicacion().getBarrio().getId());
                response.setBarrio(cliente.get().getUbicacion().getBarrio().getBarrio());
                response.setIdParroquia(cliente.get().getUbicacion().getParroquia().getId());
                response.setParroquia(cliente.get().getUbicacion().getParroquia().getParroquia());
                response.setIdCanton(cliente.get().getUbicacion().getCanton().getId());
                response.setCanton(cliente.get().getUbicacion().getCanton().getCanton());
                response.setIdProvincia(cliente.get().getUbicacion().getProvincia().getId());
                response.setProvincia(cliente.get().getUbicacion().getProvincia().getProvincia());
                response.setNombreResponsable(cliente.get().getNombreResponsable());
                response.setTelefonoResponsbale(cliente.get().getTelefonoResponsbale());
                return response;
            }else{
                throw new BadRequestException("No existe un persona con cédula" +cedula);
            }

        }else{
            throw new BadRequestException("No existe un cliente vinculado a esa persona");
        }

    }

    public PersonaUsuarioResponse login (UsuarioRequest usuarioRequest) throws Exception {
        Optional<Persona> optional = personaRepository.findByCedula(usuarioRequest.getCedula());
        if(optional.isPresent()){
            Optional<Usuario> usuarioOptional= usuarioRepository.findByPersona(optional.get());
            if(usuarioOptional.isPresent()){
                if(usuarioRequest.getClave().equals(usuarioOptional.get().getClave())){
                    return new PersonaUsuarioResponse(optional.get().getId(),usuarioOptional.get().getId(),optional.get().getCedula(),
                            optional.get().getApellidos(), optional.get().getNombres(), optional.get().getEmail(),
                            optional.get().getTelefono(), usuarioOptional.get().getClave(), usuarioOptional.get().getRoles().getId(),
                            generateTokenLogin(usuarioRequest));
                }else{
                    throw new BadRequestException("Contraseña incorrecta para email: " + usuarioRequest.getCedula());
                }
            }else{
                log.info("CEDULA NO EXISTE");
                throw new BadRequestException("Usuario no registrado como usuario");
            }
        }else{
            log.info("CEDULA NO EXISTE");
            throw new BadRequestException("Usuario no registrado");
        }
    }
        @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Persona> usuario = personaRepository.findByCedula(email);
        return new org.springframework.security.core.userdetails.User(usuario.get().getCedula(), usuario.get().getCedula(), new ArrayList<>());
    }

    public String generateTokenLogin(UsuarioRequest userRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getCedula(), userRequest.getCedula())
            );
        } catch (Exception ex) {
            log.error("INVALID: error al generar token en login de usuario con cedula: {}", userRequest.getCedula());
            throw new Exception("INAVALID");
        }
        return jwtUtil.generateToken(userRequest.getCedula());
    }

    public String generateTokenSignUp(PersonaUsuarioRequest registerRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(registerRequest.getCedula(), registerRequest.getCedula())
            );
        } catch (Exception ex) {
            log.error("INVALID: error al generar token en signup de usuario con cedula: {}", registerRequest.getCedula());
            throw new BadRequestException("INAVALID");
        }
        return jwtUtil.generateToken(registerRequest.getCedula());
    }
}
