package com.respawn.repositories;

import com.respawn.entities.Juego;
import com.respawn.entities.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends GenericRepository<Pedido> {

    @Query(value = "SELECT * FROM pedido WHERE pedido.usuario_id = :id", nativeQuery = true)
    Optional<Pedido> findPedidoByUser(@Param("id") long id);

    @Query(nativeQuery = true, value =
            "SELECT * FROM pedido p " +
            "inner join pedido_lista_pedido_detalle plpd on p.id = plpd.pedido_id " +
            "WHERE plpd.lista_pedido_detalle_id = (:id)")
    Optional<Pedido> findPedidoByPedidoDetalleId(@Param("id") Long id);
}