package com.respawn.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPedido extends GenericModel {
    private String nombre;
}