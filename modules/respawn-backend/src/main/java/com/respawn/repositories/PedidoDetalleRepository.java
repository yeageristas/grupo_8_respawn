package com.respawn.repositories;

import com.respawn.entities.PedidoDetalle;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDetalleRepository extends GenericRepository<PedidoDetalle> {

    void deleteById(long id);

}