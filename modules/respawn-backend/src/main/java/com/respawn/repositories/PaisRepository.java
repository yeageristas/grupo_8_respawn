package com.respawn.repositories;

import com.respawn.entities.Pais;

public interface PaisRepository extends GenericRepository<Pais> {
    public Pais findByNombre(String name);
}
