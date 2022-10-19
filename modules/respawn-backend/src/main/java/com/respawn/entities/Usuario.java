package com.respawn.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Usuario extends GenericModel {
    private String email;
    private String password;
    private String nombreApellido;
    private Date fechaNacimiento;
    @ManyToOne
    private Pais pais;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
