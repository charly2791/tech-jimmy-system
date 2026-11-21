package com.techjimmy.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_reparacion")
public class TicketReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false, length = 100)
    private String numeroSerieImei;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fallaReportada;

    @Column(nullable = false, length = 50)
    private String estatus = "Recibido";

    @Column(precision = 10, scale = 2)
    private BigDecimal costoEstimado;

    @Column(precision = 10, scale = 2)
    private BigDecimal costoFinal;

    private LocalDateTime fechaRecepcion = LocalDateTime.now();
    private LocalDateTime fechaEntrega;

    public void cambiarEstatus(String nuevoEstatus) {
        this.estatus = nuevoEstatus;
        if ("Entregado".equalsIgnoreCase(nuevoEstatus) && this.fechaEntrega == null) {
            this.fechaEntrega = LocalDateTime.now();
        }
    }

    public Integer getIdTicket() { return idTicket; }
    public void setIdTicket(Integer idTicket) { this.idTicket = idTicket; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getNumeroSerieImei() { return numeroSerieImei; }
    public void setNumeroSerieImei(String numeroSerieImei) { this.numeroSerieImei = numeroSerieImei; }
    public String getFallaReportada() { return fallaReportada; }
    public void setFallaReportada(String fallaReportada) { this.fallaReportada = fallaReportada; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    public BigDecimal getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(BigDecimal costoEstimado) { this.costoEstimado = costoEstimado; }
    public BigDecimal getCostoFinal() { return costoFinal; }
    public void setCostoFinal(BigDecimal costoFinal) { this.costoFinal = costoFinal; }
    public LocalDateTime getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(LocalDateTime fechaRecepcion) { this.fechaRecepcion = fechaRecepcion; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}