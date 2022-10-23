package com.respawn.entities;

import com.respawn.dto.CreateUsuarioRequest;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Audited
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends GenericModel {
    private String email;
    private String password;
    private String nombreApellido;
    private Date fechaNacimiento;
    @ManyToOne(cascade = CascadeType.ALL)
    private Pais pais;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    // i feel like i'll use this too
    static Usuario of(CreateUsuarioRequest createUsuarioRequest) {
        return Usuario.builder()
                .email(createUsuarioRequest.getEmail())
                .password(createUsuarioRequest.getPassword())
                .nombreApellido(createUsuarioRequest.getNombreApellido())
                .fechaNacimiento(createUsuarioRequest.getFechaNacimiento())
                .pais(createUsuarioRequest.getPais())
                .rol(createUsuarioRequest.getRol())
                .build();
    }
}
