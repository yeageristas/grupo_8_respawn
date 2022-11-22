package com.respawn.services;

import com.respawn.entities.Pedido;
import com.respawn.entities.PedidoDetalle;
import com.respawn.entities.Usuario;
import com.respawn.repositories.PedidoRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.PedidoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Pedido> findPedidoByPedidoDetalle(Long id) { return pedidoRepository.findPedidoByPedidoDetalleId(id) ;}

    public Optional<Pedido> findPedidoByUser(Long id) {
        return pedidoRepository.findPedidoByUser(id);
    }
    //AGREGADO
    public Optional<Pedido> findPedidoByUserPendiente(Long id){
        return pedidoRepository.findPedidoByUserPendiente(id);
    }

    public List<Pedido> findByUsuario(Long id) {
        return pedidoRepository.findByUsuario(id);
    }

    //Para generar orden
    public String generarNumeroOrden() {
        int numero=0;
        String numeroConcatenado="";

        List<Pedido> ordenes = pedidoRepository.findAll();

        List<Integer> numeros= new ArrayList<Integer>();

        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumeroPedido())));

        if (ordenes.isEmpty()) {
            numero=1;
        }else {
            numero= numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if (numero<10) { //0000001000
            numeroConcatenado="000000000"+String.valueOf(numero);
        }else if(numero<100) {
            numeroConcatenado="00000000"+String.valueOf(numero);
        }else if(numero<1000) {
            numeroConcatenado="0000000"+String.valueOf(numero);
        }else if(numero<10000) {
            numeroConcatenado="0000000"+String.valueOf(numero);
        }
        return numeroConcatenado;
    }
    
}