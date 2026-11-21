package com.techjimmy.service;

import com.techjimmy.model.*;
import com.techjimmy.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OperacionService {

    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;
    private final TicketReparacionRepository ticketRepo;
    private final VentaRepository ventaRepo;

    public OperacionService(ProductoRepository productoRepo,
                            ClienteRepository clienteRepo,
                            TicketReparacionRepository ticketRepo,
                            VentaRepository ventaRepo) {
        this.productoRepo = productoRepo;
        this.clienteRepo = clienteRepo;
        this.ticketRepo = ticketRepo;
        this.ventaRepo = ventaRepo;
    }

    public List<Producto> listarProductos() {
        return productoRepo.findAll();
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepo.save(producto);
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    public TicketReparacion crearTicket(Integer idCliente, TicketReparacion ticket) {
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + idCliente));
        ticket.setCliente(cliente);
        if (ticket.getEstatus() == null || ticket.getEstatus().isBlank()) {
            ticket.setEstatus("Recibido");
        }
        return ticketRepo.save(ticket);
    }

    public TicketReparacion actualizarEstatusTicket(Integer idTicket, String nuevoEstatus) {
        TicketReparacion ticket = ticketRepo.findById(idTicket)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado: " + idTicket));
        ticket.cambiarEstatus(nuevoEstatus);
        return ticketRepo.save(ticket);
    }

    @Transactional
    public Venta registrarVenta(Integer idCliente, Integer idProducto, int cantidad, String metodoPago) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + idProducto));

        if (producto.getStockActual() < cantidad) {
            throw new IllegalStateException("Stock insuficiente para SKU: " + producto.getSku());
        }

        producto.actualizarStock(-cantidad);
        productoRepo.save(producto);

        Venta venta = new Venta();
        if (idCliente != null) {
            Cliente cliente = clienteRepo.findById(idCliente)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + idCliente));
            venta.setCliente(cliente);
        }
        venta.setProducto(producto);
        venta.setCantidad(cantidad);
        venta.setMetodoPago(metodoPago != null ? metodoPago : "Efectivo");
        venta.setTotal(producto.getPrecioVenta().multiply(BigDecimal.valueOf(cantidad)));

        return ventaRepo.save(venta);
    }
}