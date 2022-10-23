package com.respawn.controllers;

import com.respawn.entities.Pedido;
import com.respawn.services.PedidoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/pedidos")
public class PedidoController extends GenericControllerBaseImpl<Pedido, PedidoService> {

    public PedidoController(PedidoService service) {
        super(service);
    }

}