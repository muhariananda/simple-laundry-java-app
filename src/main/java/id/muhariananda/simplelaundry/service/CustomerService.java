/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.service;

import id.muhariananda.simplelaundry.entity.Customer;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public interface CustomerService {
    Customer addCustomer(String name, String contact);
    
    Customer getCustomer(Integer id);
    
    List<Customer> searchCustomer(String name);
}
