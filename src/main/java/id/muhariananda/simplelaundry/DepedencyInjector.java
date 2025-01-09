/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry;

import id.muhariananda.simplelaundry.repository.CustomerRepository;
import id.muhariananda.simplelaundry.repository.CustomerRepositoryImpl;
import id.muhariananda.simplelaundry.repository.OrderRepository;
import id.muhariananda.simplelaundry.repository.OrderRepositoryImpl;
import id.muhariananda.simplelaundry.repository.ServiceRepository;
import id.muhariananda.simplelaundry.repository.ServiceRepositoryImpl;
import id.muhariananda.simplelaundry.service.CustomerService;
import id.muhariananda.simplelaundry.service.CustomerServiceImpl;
import id.muhariananda.simplelaundry.service.OrderService;
import id.muhariananda.simplelaundry.service.OrderServiceImpl;
import id.muhariananda.simplelaundry.service.ServiceService;
import id.muhariananda.simplelaundry.service.ServiceServiceImpl;
import id.muhariananda.simplelaundry.utils.DatabaseUtil;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author muhariananda
 */
public class DepedencyInjector {

    private static final Map<Class<?>, Object> container = new HashMap<>();

    static {
        container.put(DataSource.class, DatabaseUtil.getDataSource());

        container.put(ServiceRepository.class, new ServiceRepositoryImpl((DataSource) container.get(DataSource.class)));
        container.put(CustomerRepository.class, new CustomerRepositoryImpl((DataSource) container.get(DataSource.class)));
        container.put(OrderRepository.class, new OrderRepositoryImpl((DataSource) container.get(DataSource.class)));

        container.put(ServiceService.class, new ServiceServiceImpl((ServiceRepository) container.get(ServiceRepository.class)));
        container.put(CustomerService.class, new CustomerServiceImpl((CustomerRepository) container.get(CustomerRepository.class)));
        container.put(OrderService.class, new OrderServiceImpl((OrderRepository) container.get(OrderRepository.class)));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        return (T) container.get(clazz);
    }
}
