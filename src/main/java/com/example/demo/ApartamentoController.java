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

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
@Tag(name = "Apartamentos", description = "API para la gestión de apartamentos")
@RestController
@RequestMapping("/api/apartamentos")
public class ApartamentoController {

    private final ApartamentoService apartamentoService;

    public ApartamentoController(ApartamentoService apartamentoService) {
        this.apartamentoService = apartamentoService;
    }

    @Operation(summary = "Listar todos los apartamentos", description = "Devuelve la lista completa de apartamentos. Requiere rol de ADMIN.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    @ApiResponse(responseCode = "403", description = "Acceso denegado si no es ADMIN")
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
