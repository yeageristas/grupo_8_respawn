package com.respawn.controllers;

import com.respawn.dto.CreateUsuarioRequest;
import com.respawn.entities.Usuario;
import com.respawn.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioController extends GenericControllerBaseImpl<Usuario, UsuarioService> {

    public UsuarioController(UsuarioService service) {
        super(service);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody CreateUsuarioRequest createUsuarioRequest) throws Exception {
        this.service.save(Usuario.of(createUsuarioRequest));
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}