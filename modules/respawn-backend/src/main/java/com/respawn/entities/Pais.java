package com.respawn.entities;

import lombok.Getter;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class Pais extends GenericModel {
    String nombre;
}
