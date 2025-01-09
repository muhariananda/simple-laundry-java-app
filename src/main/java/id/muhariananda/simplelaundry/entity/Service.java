/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.entity;

import java.sql.Timestamp;

/**
 *
 * @author muhariananda
 */
public class Service {

    private int id;
    private String name;
    private double pricePerKg;
    private Timestamp createdAt;

    public Service() {
    }

    public Service(int id, String name, double pricePerKg, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id
                + ", name=" + name
                + ", pricePerKg=" + pricePerKg
                + ", createdAt=" + createdAt
                + '}';
    }

}
