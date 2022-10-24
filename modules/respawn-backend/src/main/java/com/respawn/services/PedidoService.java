package com.respawn.services;

import com.respawn.entities.Pedido;
import com.respawn.entities.PedidoDetalle;
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
    
    @Override
    public void save(Pedido p, PedidoDetalle pd) {
    	p.addPedidoDetalle(pd);
    	this.pedidoRepository.save(p);
    }
    
    @Override
    public void update(Long id, Pedido p, PedidoDetalle pd) {
    	p.setId(id);
    	p.addPedidoDetalle(pd);
        this.pedidoRepository.save(p);
    }
    
}