package com.respawn.controllers;

import com.respawn.entities.GenericModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface GenericController <E extends GenericModel> {
    public ResponseEntity<?> findAll() throws Exception;
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception;
    public ResponseEntity<?> find(@PathVariable Long id) throws Exception;
    public ResponseEntity<?> save(@RequestBody E entity) throws Exception;
}