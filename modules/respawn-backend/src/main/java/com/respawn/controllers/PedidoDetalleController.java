package com.respawn.controllers;

import com.respawn.entities.PedidoDetalle;
import com.respawn.services.PedidoDetalleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/pedidodetalles")
public class PedidoDetalleController extends GenericControllerBaseImpl<PedidoDetalle, PedidoDetalleService> {

    public PedidoDetalleController(PedidoDetalleService service) {
        super(service);
    }

}