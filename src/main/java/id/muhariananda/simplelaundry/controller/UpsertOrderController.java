/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.controller;

import id.muhariananda.simplelaundry.entity.Customer;
import id.muhariananda.simplelaundry.entity.Order;
import id.muhariananda.simplelaundry.entity.Service;
import id.muhariananda.simplelaundry.service.CustomerService;
import id.muhariananda.simplelaundry.service.OrderService;
import id.muhariananda.simplelaundry.service.ServiceService;
import id.muhariananda.simplelaundry.view.UpsertOrderFrameView;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author muhariananda
 */
public class UpsertOrderController {

    private final UpsertOrderFrameView view;
    private final ServiceService serviceService;
    private final CustomerService customerService;
    private final OrderService orderService;

    private Service selectedService = null;
    private Customer customer = null;

    public UpsertOrderController(
            UpsertOrderFrameView view,
            ServiceService serviceService,
            CustomerService customerService,
            OrderService orderService
    ) {
        this.view = view;
        this.serviceService = serviceService;
        this.customerService = customerService;
        this.orderService = orderService;

        setupView();
    }

    private void setupView() {
        setupServiceComboBox();
        setupCustomerComboBox();
        setupOrder();

        view.getDoneButton().addActionListener((e) -> {
            int option = JOptionPane.showConfirmDialog(
                    null, "Yakin tandai sudah selesai?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (option != JOptionPane.YES_OPTION) {
                return;
            }

            if (view.getId() != null) {
                orderService.updateStatusToDone(view.getId());
            }
        });

        view.getSaveButton().addActionListener((e) -> {

            try {
                String weightText = view.getWeightTextField().getText();
                if (weightText == null || weightText.trim().isEmpty()) {
                    throw new IllegalArgumentException("Berat harus disi");
                }

                float weight = Float.parseFloat(weightText);
                if (weight <= 0) {
                    throw new IllegalArgumentException("Ukuran berat harus lebih dari 0 Kg");
                }

                if (selectedService == null) {
                    throw new IllegalStateException("Pilih layanan terlebih dahulu");
                }

                if (customer == null || customer.getId() == null) {
                    String name = (String) view.getCustomerComboBox().getSelectedItem();
                    String contact = view.getContactTextField().getText();

                    if (name == null || name.trim().isEmpty()) {
                        throw new IllegalArgumentException("Nama pelanggan harus disi");
                    }

                    if (contact == null || contact.trim().isEmpty()) {
                        throw new IllegalArgumentException("Kontak pelanggan harus disi");
                    }

                    customer = customerService.addCustomer(name, contact);
                }

                double totalPrice = weight * selectedService.getPricePerKg();

                if (view.getId() == null) {
                    orderService.addOrder(customer.getId(), selectedService.getId(), weight, totalPrice);
                } else {
                    orderService.updateOrder(view.getId(), selectedService.getId(), weight, totalPrice);
                }

                if (view.getRefreshCallback() != null) {
                    view.getRefreshCallback().run();
                }

                view.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Berat tidak valid silahkan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException | IllegalStateException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void setupCustomerComboBox() {
        List<Customer> customers = customerService.searchCustomer("");

        view.getCustomerComboBox().removeAllItems();
        view.getCustomerComboBox().addItem("");

        for (Customer customer : customers) {
            view.getCustomerComboBox().addItem(customer.getName());
        }

        view.getCustomerComboBox().addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedName = (String) e.getItem();
                customer = customers.stream()
                        .filter(c -> c.getName().equals(selectedName))
                        .findFirst()
                        .orElse(null);

                if (customer != null) {
                    view.getContactTextField().setText(customer.getContact());
                }
            }
        });

        view.getCustomerComboBox().setEditable(true);
    }

    private void setupServiceComboBox() {
        List<Service> services = serviceService.searchService(null);

        view.getServiceComboBox().removeAllItems();

        for (Service service : services) {
            view.getServiceComboBox().addItem(service.getName());
        }

        view.getServiceComboBox().addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int selectedIndex = view.getServiceComboBox().getSelectedIndex();

                if (selectedIndex >= 0 && selectedIndex < services.size()) {
                    selectedService = services.get(selectedIndex);
                }
            }
        });

        int initialIndex = view.getServiceComboBox().getSelectedIndex();
        if (initialIndex >= 0 && initialIndex < services.size()) {
            selectedService = services.get(initialIndex);
        }
    }

    private void setupOrder() {
        if (view.getId() == null) {
            return;
        }

        Order order = orderService.getOrder(view.getId());

        if (order != null) {
            customer = customerService.getCustomer(order.getMemberId());
            selectedService = serviceService.getService(order.getServiceId());
            
            System.out.println(order);
            System.out.println(customer);
            System.out.println(selectedService);
        }

        if (order != null && customer != null) {
            String weigth = String.valueOf(order.getWeight());
            String totalPrice = "Rp. " + String.valueOf(order.getTotalPrice());
            String status = order.getStatus();

            view.getWeightTextField().setText(weigth);
            view.getPriceTextField().setText(totalPrice);
            view.getStatusTextField().setText(status);
            view.getCustomerComboBox().setSelectedItem(customer.getName());
            view.getContactTextField().setText(customer.getContact());
            view.getServiceComboBox().setSelectedItem(selectedService.getName());
        }
    }

}
