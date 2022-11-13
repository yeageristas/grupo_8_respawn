package com.respawn.controllers;

import com.respawn.entities.Juego;
import com.respawn.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RespawnController {

    @Autowired
    private JuegoService juegoService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private PlataformaService plataformaService;
    @Autowired
    private IdiomaService idiomaService;

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
    @GetMapping("/login")
    public String loginHandler(@Nullable Model model) {
        if (authenticationService.isLoggedIn()) {
            authenticationService.getRolesForUser().forEach(System.out::println);
            return "redirect:/";
        }
        return "views/login";
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
            model.addAttribute("idioma", this.idiomaService.findAll());
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
}
