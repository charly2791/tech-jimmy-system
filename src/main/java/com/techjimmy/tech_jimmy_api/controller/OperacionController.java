package com.techjimmy.controller;

import com.techjimmy.model.*;
import com.techjimmy.service.OperacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OperacionController {

    private final OperacionService service;

    public OperacionController(OperacionService service) {
        this.service = service;
    }

    @GetMapping("/productos")
    public List<Producto> productos() {
        return service.listarProductos();
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarProducto(producto));
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarCliente(cliente));
    }

    @PostMapping("/tickets/cliente/{idCliente}")
    public ResponseEntity<TicketReparacion> crearTicket(@PathVariable Integer idCliente,
                                                        @RequestBody TicketReparacion ticket) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearTicket(idCliente, ticket));
    }

    @PatchMapping("/tickets/{idTicket}/estatus")
    public ResponseEntity<TicketReparacion> cambiarEstatus(@PathVariable Integer idTicket,
                                                           @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.actualizarEstatusTicket(idTicket, body.get("estatus")));
    }

    @PostMapping("/ventas")
    public ResponseEntity<?> registrarVenta(@RequestParam(required = false) Integer idCliente,
                                            @RequestParam Integer idProducto,
                                            @RequestParam int cantidad,
                                            @RequestParam(defaultValue = "Efectivo") String metodoPago) {
        try {
            Venta venta = service.registrarVenta(idCliente, idProducto, cantidad, metodoPago);
            return ResponseEntity.status(HttpStatus.CREATED).body(venta);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}