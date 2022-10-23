package com.respawn.controllers;

import com.respawn.entities.Juego;
import com.respawn.services.JuegoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/juegos")
public class JuegoController extends GenericControllerBaseImpl<Juego, JuegoService> {

    public JuegoController(JuegoService service) {
        super(service);
    }
}
