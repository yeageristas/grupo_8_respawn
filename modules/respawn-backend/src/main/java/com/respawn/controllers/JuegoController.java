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
    @GetMapping("/crud")
    public String crudVideojuego(Model model){
        try {
            List<Juego> juegos = this.service.findAll(); //activos y no activos
            model.addAttribute("juegos",juegos);
            return "views/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    //FORMULARIO DE VIDEOJUEGOS
    @GetMapping("/formulario/juegos/{id}")
    public String formularioJuegos(Model model, @PathVariable("id")long id){
        try {
            model.addAttribute("categorias",this.svcCategoria.findAll());
            model.addAttribute("plataformas",this.svcPlataforma.findAll());
            model.addAttribute("idioma", this.svcIdioma.findAll());
            if(id==0){
                model.addAttribute("juegos",new Juego());
            }else{
                model.addAttribute("juegos",this.service.findById(id));
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
            model.addAttribute("categorias",this.svcCategoria.findAll());
            model.addAttribute("plataformas",this.svcPlataforma.findAll());
            model.addAttribute("idiomas", this.svcIdioma.findAll());
            if(id==0){
                this.service.save(juego); //saveOne -> save
            }else{
                this.service.update(id, juego); //updateOne -> update
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
            model.addAttribute("juegos",this.service.findById(id));
            return "views/formulario/eliminar";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/juego/{id}")
    public String desactivarJuego(Model model, @PathVariable("id")long id){
        try {
            this.service.deleteById(id);
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }


}
