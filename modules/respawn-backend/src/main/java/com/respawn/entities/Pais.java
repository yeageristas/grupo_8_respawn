package com.respawn.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

@Entity
@Getter
@Audited
@NoArgsConstructor
public class Pais extends GenericModel {
    String nombre;

    public Pais (String nombre) {
        this.nombre = nombre;
    }
}
