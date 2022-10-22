package com.respawn.controllers;

import com.respawn.entities.Categoria;
import com.respawn.services.CategoriaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/categorias")
public class CategoriaController extends GenericControllerBaseImpl<Categoria, CategoriaService> {

    public CategoriaController(CategoriaService service) {
        super(service);
    }

}
