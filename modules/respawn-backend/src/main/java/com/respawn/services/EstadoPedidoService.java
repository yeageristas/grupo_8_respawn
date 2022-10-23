package com.respawn.services;

import com.respawn.entities.EstadoPedido;
import com.respawn.repositories.EstadoPedidoRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.EstadoPedidoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoPedidoService extends GenericService<EstadoPedido> implements EstadoPedidoTransactionalService {

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    public EstadoPedidoService(GenericRepository<EstadoPedido> baseRepository) {
        super(baseRepository);
    }
}