package com.respawn.dto;

import com.respawn.entities.Pais;
import com.respawn.entities.Rol;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

// this will probably be used later
@Getter
@Setter
public class CreateUsuarioRequest {
    private String email;
    private String password;
    private String nombreApellido;
    private Date fechaNacimiento;
    private Pais pais;
    private Rol rol;
}
