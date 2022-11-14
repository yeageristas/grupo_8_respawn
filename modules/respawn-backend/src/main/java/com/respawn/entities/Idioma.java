package com.respawn.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Audited
public class Idioma extends GenericModel {
    @Column(unique = true)
    private String nombre;
}
