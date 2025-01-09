/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.repository;

import id.muhariananda.simplelaundry.entity.Order;
import id.muhariananda.simplelaundry.entity.OrderItem;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public interface OrderRepository {
    void add(Order order);
    
    Order get(Integer id);
    
    boolean update(Order order);
    
    boolean updateStatus(Integer id, String status);
    
    boolean remove(Integer id);
    
    List<OrderItem> findAll(Integer id, String status);
    
    double countIncome(String date);
}
