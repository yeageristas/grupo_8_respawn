package com.respawn.entities;

import com.respawn.entities.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "favoritos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Favoritos extends GenericModel {

    private int cantidad;

    //relacion con juego
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name= "favoritos_juego",
            joinColumns = @JoinColumn(name="favoritos_id"),
            inverseJoinColumns = @JoinColumn(name = "juego_id")
    )
    private List<Juego> juego = new ArrayList<Juego>();

    /*//Relacion con cliente -> Solo puede tener favoritos el usuario con rol de cliente
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cliente")
    private Usuario cliente;*/
}

