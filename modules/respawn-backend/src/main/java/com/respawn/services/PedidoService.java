package com.respawn.services;

import com.respawn.entities.Pedido;
import com.respawn.repositories.PedidoRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.PedidoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends GenericService<Pedido> implements PedidoTransactionalService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoService(GenericRepository<Pedido> baseRepository) {
        super(baseRepository);
    }
}