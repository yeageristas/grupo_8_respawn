package com.respawn.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Audited
public class Pedido extends GenericModel {
    private Date fecha;
    private double montoTotal;
    private String numeroPedido;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PedidoDetalle> listaPedidoDetalle;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private EstadoPedido estadoPedido;
    @ManyToOne
    private Usuario usuario;

    public Pedido(){
        this.listaPedidoDetalle = new ArrayList<>();
    }

    public void addPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.listaPedidoDetalle.add(pedidoDetalle);
    }

    public double calcularMontoTotal() {
        int suma = 0;
        if(listaPedidoDetalle.isEmpty()) {
            return 0;
        } else {
            for(var detalle : listaPedidoDetalle) {
                suma += detalle.getJuego().getPrecioSinDescuento() * detalle.getCantidad();
            }
            return suma;
        }
    }
}