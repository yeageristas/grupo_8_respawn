package com.respawn.controllers;

import com.respawn.dto.CreatePedidoRequest;
import com.respawn.entities.EstadoPedido;
import com.respawn.entities.Pedido;
import com.respawn.entities.PedidoDetalle;
import com.respawn.services.JuegoService;
import com.respawn.services.PedidoService;

import java.util.ArrayList;

import com.respawn.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/pedidos")
public class PedidoController {

	@Autowired
	protected PedidoService service;

	@Autowired
	protected UsuarioService usuarioService;

	@Autowired
	protected JuegoService juegoService;

	@GetMapping
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		try {
			this.service.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable("id") Long id) {
		try {
			var pedidoOpt = this.service.findById(id);

				return ResponseEntity.status(HttpStatus.OK).body(pedidoOpt);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody CreatePedidoRequest p) {
		try {
			Pedido pedido = new Pedido();
			pedido.setEstadoPedido(new EstadoPedido(p.getEstadoPedido().getNombre()));
			pedido.setFecha(p.getFecha());
			pedido.setMontoTotal(p.getMontoTotal());
			pedido.setNumeroPedido(String.valueOf(p.getNumeroPedido())); //CAST
			pedido.setUsuario(usuarioService.findById(p.getUsuarioId()));

			PedidoDetalle pedidoDetalle = new PedidoDetalle();
			pedidoDetalle.setCantidad(p.getCantidad());
			pedidoDetalle.setJuego(juegoService.findById(p.getJuegoId()));
			pedidoDetalle.setMontoSubtotal(p.getMontoSubtotal());

			pedido.setListaPedidoDetalle(new ArrayList<PedidoDetalle>());
			pedido.addPedidoDetalle(pedidoDetalle);

			this.service.save(pedido);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CreatePedidoRequest p) {
		try {
			Pedido pedido = new Pedido();
			pedido.setEstadoPedido(p.getEstadoPedido());
			pedido.setFecha(p.getFecha());
			pedido.setMontoTotal(p.getMontoTotal());
			pedido.setNumeroPedido(String.valueOf(p.getNumeroPedido())); //CAST
			pedido.setUsuario(usuarioService.findById(p.getUsuarioId()));

			PedidoDetalle pedidoDetalle = new PedidoDetalle();
			pedidoDetalle.setCantidad(p.getCantidad());
			pedidoDetalle.setJuego(juegoService.findById(p.getJuegoId()));
			pedidoDetalle.setMontoSubtotal(p.getMontoSubtotal());

			pedido.addPedidoDetalle(pedidoDetalle);

			this.service.update(id, pedido);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
		}
	}

}