package com.respawn.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Audited
public class PedidoDetalle extends GenericModel {
    private int cantidad;
    private double montoSubtotal;

    @ManyToOne
    private Juego juego;
}