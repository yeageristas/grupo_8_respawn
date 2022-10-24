package com.respawn.transactional_services;

import com.respawn.entities.Pedido;
import com.respawn.entities.PedidoDetalle;

public interface PedidoTransactionalService extends GenericTransactionalService<Pedido, Long> {
	
	void save(Pedido p, PedidoDetalle pd);
	void update(Long id, Pedido p, PedidoDetalle pd);
	
}

