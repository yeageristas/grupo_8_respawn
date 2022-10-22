package com.respawn.controllers;

import com.respawn.entities.EstadoJuego;
import com.respawn.services.EstadoJuegoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/estadosjuego")
public class EstadoJuegoController extends GenericControllerBaseImpl<EstadoJuego, EstadoJuegoService> {

    public EstadoJuegoController(EstadoJuegoService service) {
        super(service);
    }

}