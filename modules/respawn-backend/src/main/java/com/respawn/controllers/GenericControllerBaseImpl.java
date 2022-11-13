package com.respawn.controllers;

import com.respawn.entities.GenericModel;
import com.respawn.services.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class GenericControllerBaseImpl<E extends GenericModel, S extends GenericService<E>> implements GenericController<E>  {
    public GenericControllerBaseImpl(S service) {
        this.service = service;
    }
    protected  S service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            this.service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
        try {
            var entityOpt = this.service.findById(id);

                return ResponseEntity.status(HttpStatus.OK).body(entityOpt);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody E e) {
        try {
            this.service.save(e);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody E e) {
        try {
            this.service.update(id, e);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("{\"error\":\"%s\"}", ex.getMessage()));
        }
    }
}
