package com.respawn.entities;

import com.respawn.entities.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotEmpty(message = "El nombre no puede quedar vacío.")
    private String nombre;

    private double precioSinDescuento;

    private double precioConDescuento;

    @Size(min=10,message= "La descripción es muy corta.")
    private String descripcion;

    private int cantidadVentas;

    private String imagenPortada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="La fecha no puede ser nula.")
    @PastOrPresent(message="La fecha debe ser igual o menor a la fecha actual.")
    private Date fechaLanzamiento;

    private String tamanio;

    private boolean oferta;
    private boolean activo = true;

    //RELACIONES

    // relacion con categoria
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;

    // relacion con plataforma
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_plataforma", nullable = false)
    private Plataforma plataforma;

    //relacion con idioma
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name= "juego_idioma",
            joinColumns = @JoinColumn(name="juego_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id")
    )
    private List<Idioma> idioma = new ArrayList<Idioma>();
}
