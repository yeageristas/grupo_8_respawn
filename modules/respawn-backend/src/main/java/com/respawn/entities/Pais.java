package com.respawn.entities;

import lombok.Getter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

@Entity
@Getter
@Audited
public class Pais extends GenericModel {
    String nombre;
}
