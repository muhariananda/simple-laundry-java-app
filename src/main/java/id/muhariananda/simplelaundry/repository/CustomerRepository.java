/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.repository;

import id.muhariananda.simplelaundry.entity.Customer;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public interface CustomerRepository {
    Customer insert(Customer customer);
    
    Customer get(Integer id);
    
    List<Customer> search(String query);
}
