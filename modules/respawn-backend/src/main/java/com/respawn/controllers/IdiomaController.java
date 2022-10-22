package com.respawn.controllers;

import com.respawn.entities.Idioma;
import com.respawn.services.IdiomaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/idiomas")
public class IdiomaController extends GenericControllerBaseImpl<Idioma, IdiomaService> {

    public IdiomaController(IdiomaService service) {
        super(service);
    }

}