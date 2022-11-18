package com.respawn.entities;

import com.respawn.dto.CreateUsuarioRequest;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Collections;
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
    public static Usuario of(CreateUsuarioRequest createUsuarioRequest) {
        return Usuario.builder()
                .email(createUsuarioRequest.getEmail())
                .password(createUsuarioRequest.getPassword())
                .nombreApellido(createUsuarioRequest.getNombreApellido())
                .fechaNacimiento(createUsuarioRequest.getFechaNacimiento())
                .build();
    }

    public static User of(Usuario usuario) {
        return new User(usuario.getEmail(), usuario.getPassword(), Collections.singleton(usuario.getRol().getAuthority()));
    }
}
