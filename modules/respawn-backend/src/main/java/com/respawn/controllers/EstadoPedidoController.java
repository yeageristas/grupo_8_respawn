package com.respawn.controllers;

import com.respawn.entities.EstadoPedido;
import com.respawn.services.EstadoPedidoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/estadospedido")
public class EstadoPedidoController extends GenericControllerBaseImpl<EstadoPedido, EstadoPedidoService> {

    public EstadoPedidoController(EstadoPedidoService service) {
        super(service);
    }

}