package com.example.demo;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/apartamentos")
public class ApartamentoController {

    private final ApartamentoService apartamentoService;

    public ApartamentoController(ApartamentoService apartamentoService) {
        this.apartamentoService = apartamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Apartamento>> listar(@RequestHeader(value = "X-User-Role", required = false) String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(apartamentoService.findAll());
    }

    @GetMapping("/mio")
    public ResponseEntity<Apartamento> consultarPropio(
            @RequestHeader(value = "X-User-Id", required = false) String propietarioId) {
        if (propietarioId == null || propietarioId.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Apartamento apartamento = apartamentoService.findByPropietarioId(propietarioId);
        return apartamento == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(apartamento);
    }

    @PostMapping
    public ResponseEntity<Apartamento> registrar(@RequestHeader(value = "X-User-Role", required = false) String role,
                                                 @RequestBody Apartamento apartamento) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(apartamentoService.save(apartamento));
    }
}
