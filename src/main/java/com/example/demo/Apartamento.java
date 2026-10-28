package com.example.demo;
import java.util.UUID;

public class Apartamento {

    private String id;
    private String numero;
    private String torre;
    private double area;
    private String propietarioId;

    public Apartamento() {
        this.id = UUID.randomUUID().toString();
    }

    public Apartamento(String id, String numero, String torre, double area, String propietarioId) {
        this.id = id;
        this.numero = numero;
        this.torre = torre;
        this.area = area;
        this.propietarioId = propietarioId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getTorre() { return torre; }
    public void setTorre(String torre) { this.torre = torre; }
    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
    public String getPropietarioId() { return propietarioId; }
    public void setPropietarioId(String propietarioId) { this.propietarioId = propietarioId; }
}



