package com.respawn.controllers;

import com.respawn.dto.CreateUsuarioRequest;
import com.respawn.entities.Rol;
import com.respawn.entities.Usuario;
import com.respawn.repositories.PaisRepository;
import com.respawn.services.PaisService;
import com.respawn.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioController extends GenericControllerBaseImpl<Usuario, UsuarioService> {

    @Autowired
    private PaisService paisService;

    public UsuarioController(UsuarioService service) {
        super(service);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody CreateUsuarioRequest createUsuarioRequest) throws Exception {
        var pais = this.paisService.findByNombre(createUsuarioRequest.getPais());
        var rol = Rol.valueOf(createUsuarioRequest.getRol());
        var usuario = Usuario.of(createUsuarioRequest);
        usuario.setPais(pais);
        usuario.setRol(rol);
        this.service.save(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}