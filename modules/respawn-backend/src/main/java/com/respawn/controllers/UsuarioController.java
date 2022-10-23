package com.respawn.controllers;

import com.respawn.entities.Usuario;
import com.respawn.services.UsuarioService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioController extends GenericControllerBaseImpl<Usuario, UsuarioService> {

    public UsuarioController(UsuarioService service) {
        super(service);
    }

}