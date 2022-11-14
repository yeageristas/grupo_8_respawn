package com.respawn.dto;

import java.util.Date;

import com.respawn.entities.EstadoPedido;
import com.respawn.entities.Juego;
import com.respawn.entities.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePedidoRequest {
    private Date fecha;
    private double montoTotal;
    private int numeroPedido;
    private EstadoPedido estadoPedido;
    private long usuarioId;
    private int cantidad;
    private double montoSubtotal;
    private long juegoId;
}
