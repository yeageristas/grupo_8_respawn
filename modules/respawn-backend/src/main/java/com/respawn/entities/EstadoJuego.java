package com.respawn.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class EstadoJuego extends GenericModel {

    private String nombre;

}
