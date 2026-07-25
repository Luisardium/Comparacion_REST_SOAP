package com.example.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class SumaControlador {

    @PostMapping("/suma")
    public ResponseEntity<SumaResponse> sumar(@RequestBody SumaRequest request) {
        int resultado = request.getA() + request.getB();
        return ResponseEntity.ok(new SumaResponse(resultado));
    }
}