package com.respawn.dto;

import com.respawn.entities.Pais;
import com.respawn.entities.Rol;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

// this will probably be used later
@Getter
@Setter
public class CreateUsuarioRequest {
    private String email;
    private String password;
    private String nombreApellido;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    private String pais;
    private String rol;
}
