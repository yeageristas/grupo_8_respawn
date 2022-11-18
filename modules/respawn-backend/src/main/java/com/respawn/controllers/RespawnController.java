package com.respawn.controllers;

import com.respawn.dto.CreateUsuarioRequest;
import com.respawn.entities.*;
import com.respawn.repositories.PaisRepository;
import com.respawn.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
            final String role = authenticationService.getRolesForUser().get(0);
            System.out.println(role);
            model.addAttribute("juegos", juegos);
            model.addAttribute("role", role);
            return "views/home";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(Model model) {
        try {
            final String userEmail = authenticationService.getUserEmail();
            var usuario = usuarioService.findByEmail(userEmail);
            var pedido = this.pedidoService.findPedidoByUser(usuario.get().getId());

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

    @PostMapping ("/doRegister")
    public String registerHandler(Model model, @ModelAttribute("usuario") CreateUsuarioRequest usuario) {
        try {
            var pais = this.paisService.findByNombre(usuario.getPais());
            var rol = Rol.valueOf(usuario.getRol());
            var usuarioNuevo = Usuario.of(usuario);
            usuarioNuevo.setPais(pais);
            usuarioNuevo.setRol(rol);
            this.usuarioService.save(usuarioNuevo);
            return "redirect:/";
        } catch(Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        try {
            var juego = this.juegoService.findById(id);
            var juegos = this.juegoService.findAllByActivo();
            model.addAttribute("juego", juego);
            model.addAttribute("juegos", juegos);
            return "views/product-detail-view.html";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping(value = "/search")
    public String busquedaJuego(Model model, @RequestParam(value ="query", required = false) String q) {
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
    public String crudVideojuego(Model model){
        try {
            List<Juego> juegos = this.juegoService.findAll(); //activos y no activos
            model.addAttribute("juegos",juegos);
            return "views/crud-juego";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    //FORMULARIO DE VIDEOJUEGOS
    @GetMapping("/formulario/juegos/{id}")
    public String formularioJuegos(Model model, @PathVariable("id")long id){
        try {
            model.addAttribute("categorias",this.categoriaService.findAll());
            model.addAttribute("plataformas",this.plataformaService.findAll());
            model.addAttribute("idiomas", this.idiomaService.findAll());
            if(id==0){
                model.addAttribute("juegos",new Juego());
            }else{
                model.addAttribute("juegos",this.juegoService.findById(id));
            }
            return "views/formulario/juegos";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/juegos/{id}")
    public String guardarJuegos(
            @Valid @ModelAttribute("juegos") Juego juego,
            Model model,@PathVariable("id")long id
    ) {
        try {
            model.addAttribute("categorias",this.categoriaService.findAll());
            model.addAttribute("plataformas",this.plataformaService.findAll());
            model.addAttribute("idiomas", this.idiomaService.findAll());
            if(id==0){
                this.juegoService.save(juego); //saveOne -> save
            }else{
                this.juegoService.update(id, juego); //updateOne -> update
            }
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/eliminar/juego/{id}")
    public String eliminarJuegos(Model model, @PathVariable("id")long id){
        try {
            model.addAttribute("juegos",this.juegoService.findById(id));
            return "views/formulario/eliminar";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/juego/{id}")
    public String desactivarJuego(Model model, @PathVariable("id")long id){
        try {
            this.juegoService.deleteById(id);
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    @PostMapping("/eliminar-carrito/juego/{id}")
    public String eliminarJuegoCarrito(Model model, @PathVariable("id")long id){
        try {
            this.pedidoDetalleService.deleteById(id);
            return "redirect:/shopping-cart";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }
}
