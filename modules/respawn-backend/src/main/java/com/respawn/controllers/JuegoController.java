package com.respawn.controllers;

import com.respawn.entities.Juego;
import com.respawn.services.CategoriaService;
import com.respawn.services.IdiomaService;
import com.respawn.services.JuegoService;
import com.respawn.services.PlataformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/juegos")
public class JuegoController extends GenericControllerBaseImpl<Juego, JuegoService> {
    @Autowired
    private CategoriaService svcCategoria;
    @Autowired
    private PlataformaService svcPlataforma;
    @Autowired
    private IdiomaService svcIdioma;

    public JuegoController(JuegoService service) {super(service);}

    //-------------------------------------------------------------------
    @GetMapping("/inicio")
    public String inicio(Model model) {
        try {
            List<Juego> juegos = this.service.findAllByActivo();
            model.addAttribute("juegos", juegos);

            return "views/inicio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalleVideojuego(Model model, @PathVariable("id") long id) {
        try {
            Juego juegos = this.service.findByIdAndActivo(id);
            model.addAttribute("juegos",juegos);
            return "views/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    //METODO PARA LA BUSQUEDA
    @GetMapping(value = "/search")
    public String busquedaVideojuego(Model model, @RequestParam(value ="query", required = false)String q){
        try {
            List<Juego> juegos = this.service.findByTitle(q);
            model.addAttribute("juegos", juegos);
            model.addAttribute("resultado",q);
            return "views/search";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    //VISTA DEL CRUD
}
