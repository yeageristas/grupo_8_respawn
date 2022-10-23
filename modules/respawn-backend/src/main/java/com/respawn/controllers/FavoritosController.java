package com.respawn.controllers;

import com.respawn.entities.Favoritos;
import com.respawn.services.FavoritosService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/favoritos")
public class FavoritosController extends GenericControllerBaseImpl<Favoritos, FavoritosService> {

    public FavoritosController(FavoritosService service) {
        super(service);
    }
}
