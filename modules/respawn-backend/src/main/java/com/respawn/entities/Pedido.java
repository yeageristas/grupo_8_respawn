package com.respawn.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;

@Entity
@Getter
@Setter
@Audited
public class Pedido extends GenericModel {
    private Date fecha;
    private double montoTotal;
    private int numeroPedido;

    @OneToMany
    private ArrayList<PedidoDetalle> listaPedidoDetalle;
    @ManyToOne
    private EstadoPedido estadoPedido;
    @ManyToOne
    private Usuario usuario;
}