package com.respawn.controllers;

import com.respawn.dto.CreateUsuarioRequest;
import com.respawn.entities.*;
import com.respawn.repositories.PaisRepository;
import com.respawn.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
public class RespawnController {

    @Autowired
    private JuegoService juegoService;

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoDetalleService pedidoDetalleService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private PlataformaService plataformaService;
    @Autowired
    private IdiomaService idiomaService;

    @Autowired
    private PaisService paisService;

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Juego> juegos = this.juegoService.findAllByActivo();
            String role;
            if (authenticationService.isLoggedIn()) {
                role = authenticationService.getRolesForUser().get(0);
                model.addAttribute("role", role);
            } else {
                role = "";
            }
            model.addAttribute("juegos", juegos);
            return "views/home";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("/ofertas")
    public String ofertas (Model model){
        try{
            List<Juego> juegos = this.juegoService.findAllByActivo();
            String role;
            List<Juego> juegosOferta = new ArrayList<Juego>();
            for(Juego juego :juegos) {
                if (juego.isOferta()){
                    juegosOferta.add(juego);
                }
            }
            model.addAttribute("juegos", juegosOferta);
            return "views/ofertas";

        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(Model model) {
        try {
            final String userEmail = authenticationService.getUserEmail();
            var usuario = usuarioService.findByEmail(userEmail);
            var pedido = this.pedidoService.findPedidoByUserPendiente(usuario.get().getId());

            if (pedido.isPresent()) {
                model.addAttribute("detalles", pedido.get().getListaPedidoDetalle());
                var total = pedido.get().calcularMontoTotal();
                model.addAttribute("total", total);
            } else {
                model.addAttribute("detalles", Collections.emptyList());
                model.addAttribute("total", 0);
            }

            return "views/shopping-cart";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/shopping-cart2")
    public String shoppingCart2(Model model) {
        return "views/shopping-cart-2";
    }

    @GetMapping("/login")
    public String loginHandler(@Nullable Model model) {
        if (authenticationService.isLoggedIn()) {
            authenticationService.getRolesForUser().forEach(System.out::println);
            return "redirect:/";
        }
        return "views/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        try {
            model.addAttribute("paises", paisService.findAll());
            model.addAttribute("usuario", new CreateUsuarioRequest());
            return "views/register";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/doRegister")
    public String registerHandler(Model model, @ModelAttribute("usuario") CreateUsuarioRequest usuario) {
        try {
            var pais = this.paisService.findByNombre(usuario.getPais());
            var rol = Rol.valueOf(usuario.getRol());
            var usuarioNuevo = Usuario.of(usuario);
            usuarioNuevo.setPais(pais);
            usuarioNuevo.setRol(rol);
            this.usuarioService.save(usuarioNuevo);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        try {
            var juego = this.juegoService.findById(id);
            var juegos = this.juegoService.findAllByActivo();
            model.addAttribute("juegoDetalle", juego);
            model.addAttribute("categoria", juego.getCategoria().getNombre());
            model.addAttribute("plataforma", juego.getPlataforma().getNombre());
            model.addAttribute("juegos", juegos);
            return "views/product-detail-view.html";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/search")
    public String busquedaJuego(Model model, @RequestParam(value = "query", required = false) String q) {
        try {
            List<Juego> juegos = this.juegoService.findByTitle(q);
            model.addAttribute("juegos", juegos);
            model.addAttribute("resultado", q);
            return "views/search";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/crud")
    public String crudVideojuego(Model model) {
        try {
            List<Juego> juegos = this.juegoService.findAll(); //activos y no activos
            model.addAttribute("juegos", juegos);
            return "views/crud-juego";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    //FORMULARIO DE VIDEOJUEGOS
    @GetMapping("/formulario/juegos/{id}")
    public String formularioJuegos(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("categorias", this.categoriaService.findAll());
            model.addAttribute("plataformas", this.plataformaService.findAll());
            model.addAttribute("idiomas", this.idiomaService.findAll());
            if (id == 0) {
                var juegoNuevo = new Juego();
                juegoNuevo.setId(0L);
                model.addAttribute("juego", juegoNuevo);
            } else {
                model.addAttribute("juego", this.juegoService.findById(id));
            }
            return "views/formulario/juegos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/juegos/{id}")
    public String guardarJuegos(
            @Valid @ModelAttribute("juego") Juego juego,
            BindingResult result,
            Model model, @PathVariable("id") long id
    ) {
        try {
            model.addAttribute("categorias", this.categoriaService.findAll());
            model.addAttribute("plataformas", this.plataformaService.findAll());
            model.addAttribute("idiomas", this.idiomaService.findAll());
            if (result.hasErrors()) {
                return "views/formulario/juegos";
            }
            if (id == 0) {
                juego.setId(null);
                this.juegoService.save(juego); //saveOne -> save
            } else {
                this.juegoService.update(id, juego); //updateOne -> update
            }
            return "redirect:/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/eliminar/juego/{id}")
    public String eliminarJuegos(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("idJuego", id);
            return "views/formulario/eliminar";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/juego/{id}")
    public String desactivarJuego(Model model, @PathVariable("id") long id) {
        try {
            this.juegoService.deleteById(id);
            return "redirect:/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    @GetMapping("/eliminar-carrito/juego/{id}")
    public String eliminarJuegoCarrito(Model model, @PathVariable("id") long id) {
        try {
            var pedido = this.pedidoService.findPedidoByPedidoDetalle(id);
            if(pedido.isPresent()) {
                var pedidoDetalle = this.pedidoDetalleService.findById(id);
                pedido.get().getListaPedidoDetalle().remove(pedidoDetalle);
                pedidoService.save(pedido.get());
                pedidoDetalleService.deleteById(id);
            }

            return "redirect:/shopping-cart";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    @PostMapping("/agregar/juego/{id}")
    public String agregarJuegoCarrito(Model model, @PathVariable("id") long id, @RequestParam Integer cantidad) {
        try {
            // Sweet Child O' Mine
            final String userEmail = authenticationService.getUserEmail();
            var usuario = usuarioService.findByEmail(userEmail);
            var pedido = pedidoService.findPedidoByUserPendiente(usuario.get().getId()); //Modificado
            Juego juego = this.juegoService.findById(id);
            if (pedido.isPresent()) {
                //validar que el producto no se añada 2 veces
                Long idProducto= juego.getId();
                boolean ingresado=pedido.get().getListaPedidoDetalle().stream().anyMatch(p -> p.getJuego().getId()==idProducto);
                if (!ingresado) {
                    PedidoDetalle pedidoDetalle = new PedidoDetalle();
                    pedidoDetalle.setJuego(juego);
                    pedidoDetalle.setCantidad(cantidad);
                    pedidoDetalle.setMontoSubtotal(juego.getPrecioSinDescuento()*pedidoDetalle.getCantidad());
                    pedidoService.save(pedido.get(), pedidoDetalle);
                }
                else{
                    for(PedidoDetalle  pDet : pedido.get().getListaPedidoDetalle()) {
                        if (pDet.getJuego().getId() == id){ //detalle que sea del mismo juego
                            var idDet = pDet.getId();
                            pDet.setCantidad(pDet.getCantidad() + cantidad );
                            pDet.setMontoSubtotal(juego.getPrecioSinDescuento()*pDet.getCantidad());
                            pedidoDetalleService.update(idDet, pDet);
                            break;
                        }
                    }

                }

            } else {
                var pedidoNuevo = new Pedido();
                EstadoPedido estadoPedido = new EstadoPedido("Pendiente");
                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                pedidoDetalle.setJuego(juego);
                pedidoNuevo.setEstadoPedido(estadoPedido);
                pedidoNuevo.setUsuario(usuario.get());
                //MOdificado
                pedidoNuevo.setNumeroPedido("0");
                pedidoDetalle.setCantidad(cantidad);
                pedidoDetalle.setMontoSubtotal(juego.getPrecioSinDescuento()*pedidoDetalle.getCantidad());
                pedidoService.save(pedidoNuevo, pedidoDetalle);
            }
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    @PostMapping("/comprar-ahora/juego/{id}")
    public String comprarAhora(Model model, @PathVariable("id") long id) {
        try {
            // Sweet Child O' Mine
            final String userEmail = authenticationService.getUserEmail();
            var usuario = usuarioService.findByEmail(userEmail);
            var pedido = pedidoService.findPedidoByUserPendiente(usuario.get().getId()); //Modificado
            Juego juego = this.juegoService.findById(id);
            if (pedido.isPresent()) {
                //validar que el producto no se añada 2 veces
                Long idProducto= juego.getId();
                boolean ingresado=pedido.get().getListaPedidoDetalle().stream().anyMatch(p -> p.getJuego().getId()==idProducto);
                if (!ingresado) {
                    PedidoDetalle pedidoDetalle = new PedidoDetalle();
                    pedidoDetalle.setJuego(juego);
                    pedidoDetalle.setCantidad(1);
                    pedidoDetalle.setMontoSubtotal(juego.getPrecioSinDescuento()*pedidoDetalle.getCantidad());
                    pedidoService.save(pedido.get(), pedidoDetalle);
                }
                else{
                    for(PedidoDetalle  pDet : pedido.get().getListaPedidoDetalle()) {
                        if (pDet.getJuego().getId() == id){ //detalle que sea del mismo juego
                            var idDet = pDet.getId();
                            pDet.setCantidad(pDet.getCantidad() + 1 );
                            pDet.setMontoSubtotal(juego.getPrecioSinDescuento()*pDet.getCantidad());
                            pedidoDetalleService.update(idDet, pDet);
                            break;
                        }
                    }

                }

            } else {
                var pedidoNuevo = new Pedido();
                EstadoPedido estadoPedido = new EstadoPedido("Pendiente");
                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                pedidoDetalle.setJuego(juego);
                pedidoNuevo.setEstadoPedido(estadoPedido);
                pedidoNuevo.setUsuario(usuario.get());
                //MOdificado
                pedidoNuevo.setNumeroPedido("0");
                pedidoDetalle.setCantidad(1);
                pedidoDetalle.setMontoSubtotal(juego.getPrecioSinDescuento()*pedidoDetalle.getCantidad());
                pedidoService.save(pedidoNuevo, pedidoDetalle);
            }
            return "redirect:/shopping-cart";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    @GetMapping("/detallePedido")
    public String detallePedido(Model model){
            final String userEmail = authenticationService.getUserEmail();
            var usuario = usuarioService.findByEmail(userEmail);
            var pedido = pedidoService.findPedidoByUserPendiente(usuario.get().getId()); //Modificado

            model.addAttribute("detalles",  pedido.get().getListaPedidoDetalle());
            model.addAttribute("pedido", pedido.get());
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("pais", usuario.get().getPais().getNombre());
            var total = pedido.get().calcularMontoTotal();
            model.addAttribute("total", total);

        return "views/detallesDelPedido";
    }

    //se guarda la orden de la compra realizada
    @GetMapping("/compraRealizada")
    public String compraRealizada() throws Exception {
        final String userEmail = authenticationService.getUserEmail();
        var usuario = usuarioService.findByEmail(userEmail);
        Pedido pedidoRealizado = new Pedido();
        var pedidoPendiente = pedidoService.findPedidoByUserPendiente(usuario.get().getId()); //Modificado

        Date fechaCreacion = new Date();
        EstadoPedido estadoPedido = new EstadoPedido("Realizado");
        pedidoRealizado.setFecha(fechaCreacion);
        pedidoRealizado.setNumeroPedido(pedidoService.generarNumeroOrden());
        pedidoRealizado.setEstadoPedido(estadoPedido);
        var total = pedidoPendiente.get().calcularMontoTotal();
        pedidoRealizado.setMontoTotal(total);
        pedidoRealizado.setUsuario(usuario.get());
        ///limpiar lista y orden
        pedidoPendiente.get().getListaPedidoDetalle().clear();
        //usuario
        pedidoService.save(pedidoRealizado);
        return "views/pedidoCompletado";
    }
    //Compras del usuario
    @GetMapping("/compras")
    public String obtenerCompras(Model model) {
        final String userEmail = authenticationService.getUserEmail();
        var usuario = usuarioService.findByEmail(userEmail);

        List<Pedido> ordenes= pedidoService.findByUsuario(usuario.get().getId());
        model.addAttribute("pedidos", ordenes);

        return "views/compras";
    }

}
