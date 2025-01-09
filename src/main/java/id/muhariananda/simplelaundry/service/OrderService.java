/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.service;

import id.muhariananda.simplelaundry.entity.Order;
import id.muhariananda.simplelaundry.entity.OrderItem;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public interface OrderService {
    void addOrder(int memberId, int serviceId, float weight, double totalPrice);
    
    void updateOrder(int orderId, int serviceId, float weight, double totalPrice);
    
    void deleteOrder(Integer id);
    
    Order getOrder(Integer id);
    
    List<OrderItem> findAllOrders(Integer id, String status);
    
    void updateStatusToDone(Integer id);
    
    double getTotalIncomeByDate(String date);
}
