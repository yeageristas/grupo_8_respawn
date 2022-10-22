package com.respawn.controllers;

import com.respawn.entities.Plataforma;
import com.respawn.services.PlataformaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/plataformas")
public class PlataformaController extends GenericControllerBaseImpl<Plataforma, PlataformaService> {

    public PlataformaController(PlataformaService service) {
        super(service);
    }

}