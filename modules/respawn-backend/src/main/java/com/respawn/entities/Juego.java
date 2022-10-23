package com.respawn.entities;

import com.respawn.entities.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "juego")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Juego extends GenericModel {

    private String nombre;
    private double precioSinDescuento;
    private double precioConDescuento;
    private String descripcion;
    private int cantidadVentas;
    private String imagenPortada;
    private Date fechaLanzamiento;
    private String tamaño;
    private boolean Oferta;

    //RELACIONES
    // relacion con estado
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_estadojuego")
    private EstadoJuego estadoJuego;

    // relacion con categoria
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;

    // relacion con plataforma
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_plataforma")
    private Plataforma plataforma;

    //relacion con idioma
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name= "juego_idioma",
            joinColumns = @JoinColumn(name="juego_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id")
    )
    private List<Idioma> idioma = new ArrayList<Idioma>();
}
