/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.service;

import id.muhariananda.simplelaundry.entity.Service;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public interface ServiceService {
    void addService(String name, double price);
    
    Service getService(int id);
    
    void updateService(int id, String name, double price);
    
    void deleteService(int id);
    
    List<Service> searchService(String name);
}
