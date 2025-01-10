/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry;

import id.muhariananda.simplelaundry.controller.IncomeController;
import id.muhariananda.simplelaundry.controller.OrderController;
import id.muhariananda.simplelaundry.controller.ServiceController;
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
import id.muhariananda.simplelaundry.view.IncomePanelView;
import id.muhariananda.simplelaundry.view.OrderPanelView;
import id.muhariananda.simplelaundry.view.ServicePanelView;
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
        // data source
        container.put(DataSource.class, DatabaseUtil.getDataSource());

        // repositories
        container.put(ServiceRepository.class, new ServiceRepositoryImpl((DataSource) container.get(DataSource.class)));
        container.put(CustomerRepository.class, new CustomerRepositoryImpl((DataSource) container.get(DataSource.class)));
        container.put(OrderRepository.class, new OrderRepositoryImpl((DataSource) container.get(DataSource.class)));

        // services
        container.put(ServiceService.class, new ServiceServiceImpl((ServiceRepository) container.get(ServiceRepository.class)));
        container.put(CustomerService.class, new CustomerServiceImpl((CustomerRepository) container.get(CustomerRepository.class)));
        container.put(OrderService.class, new OrderServiceImpl((OrderRepository) container.get(OrderRepository.class)));
        
        // views
        container.put(ServicePanelView.class, new ServicePanelView());
        container.put(OrderPanelView.class, new OrderPanelView());
        container.put(IncomePanelView.class, new IncomePanelView());

        // controllers
        container.put(ServiceController.class, new ServiceController(
                (ServicePanelView) container.get(ServicePanelView.class),
                (ServiceService) container.get(ServiceService.class)
        ));
        container.put(OrderController.class, new OrderController(
                (OrderPanelView) container.get(OrderPanelView.class),
                (OrderService) container.get(OrderService.class)
        ));
        container.put(IncomeController.class, new IncomeController(
                (IncomePanelView) container.get(IncomePanelView.class),
                (OrderService) container.get(OrderService.class)
        ));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        return (T) container.get(clazz);
    }
}
