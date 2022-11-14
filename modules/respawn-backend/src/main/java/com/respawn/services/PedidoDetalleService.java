package com.respawn.services;

import com.respawn.entities.PedidoDetalle;
import com.respawn.repositories.PedidoDetalleRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.PedidoDetalleTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoDetalleService extends GenericService<PedidoDetalle> implements PedidoDetalleTransactionalService {

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    public PedidoDetalleService(GenericRepository<PedidoDetalle> baseRepository) {
        super(baseRepository);
    }

    public void deleteById(long id) {
        pedidoDetalleRepository.deleteById(id);
    }
}