/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.service;

import id.muhariananda.simplelaundry.entity.Customer;
import id.muhariananda.simplelaundry.repository.CustomerRepository;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(String name, String contact) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setContact(contact);
        
        return customerRepository.insert(customer);
    }

    @Override
    public Customer getCustomer(Integer id) {
        return customerRepository.get(id);
    }

    @Override
    public List<Customer> searchCustomer(String name) {
        return customerRepository.search(name);
    }
}
