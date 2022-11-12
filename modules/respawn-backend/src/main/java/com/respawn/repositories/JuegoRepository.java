package com.respawn.repositories;

import com.respawn.entities.Juego;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends GenericRepository<Juego> {
    @Query(value = "SELECT * FROM juego WHERE juego.nombre LIKE %:q% AND juego.activo = true", nativeQuery = true)
    List<Juego> findByTitle(@Param("q")String q);
}
