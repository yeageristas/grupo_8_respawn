package com.respawn.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Audited
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
