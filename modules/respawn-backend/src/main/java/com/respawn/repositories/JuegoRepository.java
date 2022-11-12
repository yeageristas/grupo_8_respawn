package com.respawn.repositories;

import com.respawn.entities.Juego;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JuegoRepository extends GenericRepository<Juego> {

    @Query(value = "SELECT * FROM juego  WHERE juego.activo=true", nativeQuery = true)
    List<Juego> findAllByActivo();

    @Query(value = "SELECT * FROM juego  WHERE juego.id = :id AND juego.activo=true", nativeQuery = true)
    Optional<Juego> findByIdAndActivo(@Param("id") long id);

    //METODO PARA LA BUSQUEDA
    @Query(value = "SELECT * FROM juego WHERE juego.nombre LIKE %:q% AND juego.activo=true", nativeQuery = true)
    List<Juego> findByTitle(@Param("q")String q);
}
