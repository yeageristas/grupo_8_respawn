package com.respawn.controllers;

import com.respawn.entities.Juego;
import com.respawn.services.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RespawnController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Juego> juegos = this.juegoService.findAll();
            model.addAttribute("juegos", juegos);
            return "views/home";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("/login")
    public String loginHandler(@Nullable Model model) {
        return "views/login";
    }

    @GetMapping("/detalle/{:id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        try {
            var juego = this.juegoService.findById(id);
            if(juego.isPresent()) {
                model.addAttribute("juego", juego.get());
                return "views/product-detail.view.html";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
