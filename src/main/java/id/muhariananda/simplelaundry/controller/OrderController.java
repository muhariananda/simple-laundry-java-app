/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.controller;

import id.muhariananda.simplelaundry.DepedencyInjector;
import id.muhariananda.simplelaundry.entity.OrderItem;
import id.muhariananda.simplelaundry.entity.Status;
import id.muhariananda.simplelaundry.service.CustomerService;
import id.muhariananda.simplelaundry.service.OrderService;
import id.muhariananda.simplelaundry.service.ServiceService;
import id.muhariananda.simplelaundry.utils.DateUtil;
import id.muhariananda.simplelaundry.view.OrderPanelView;
import id.muhariananda.simplelaundry.view.UpsertOrderFrameView;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author muhariananda
 */
public class OrderController {

    private final OrderPanelView view;
    private final OrderService orderService;

    private String selectedStatus;

    public OrderController(OrderPanelView view, OrderService orderService) {
        this.view = view;
        this.orderService = orderService;

        showOrders(null, Status.ON_PROSSES);
        setupView();
    }

    private void setupView() {
        setupStatusComboBox();

        view.getRefreshButton().addActionListener((e) -> {
            showOrders(null, selectedStatus);
        });

        view.getSearchButton().addActionListener((e) -> {
            String id = view.getSearchTextField().getText();
            
            if (!id.isEmpty()) {
                showOrders(Integer.valueOf(id), selectedStatus);
            }
        });

        view.getDeleteButton().addActionListener((e) -> {
            int selectedRow = view.getViewTable().getSelectedRow();

            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu");
            }

            int option = JOptionPane.showConfirmDialog(
                    null, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (option != JOptionPane.YES_OPTION) {
                return;
            }

            TableModel model = view.getViewTable().getModel();
            int id = (int) model.getValueAt(selectedRow, 0);

            orderService.deleteOrder(id);
            showOrders(null, selectedStatus);
        });

        view.getDoneButton().addActionListener((e) -> {
            int selectedRow = view.getViewTable().getSelectedRow();

            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu");
            }

            int option = JOptionPane.showConfirmDialog(
                    null, "Yakin tandai sudah selesai?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (option != JOptionPane.YES_OPTION) {
                return;
            }

            TableModel model = view.getViewTable().getModel();
            int id = (int) model.getValueAt(selectedRow, 0);

            orderService.updateStatusToDone(id);
            showOrders(null, selectedStatus);
        });

        view.getAddButton().addActionListener((e) -> {
            ServiceService serviceService = DepedencyInjector.getInstance(ServiceService.class);
            CustomerService customerService = DepedencyInjector.getInstance(CustomerService.class);

            UpsertOrderFrameView frame = new UpsertOrderFrameView(null, () -> {
                showOrders(null, selectedStatus);
            });

            new UpsertOrderController(frame, serviceService, customerService, this.orderService);
            frame.setVisible(true);
        });

        view.getUpdateButton().addActionListener((e) -> {
            int selectedRow = view.getViewTable().getSelectedRow();

            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu");
                return;
            }

            TableModel model = view.getViewTable().getModel();
            int id = (int) model.getValueAt(selectedRow, 0);

            ServiceService serviceService = DepedencyInjector.getInstance(ServiceService.class);
            CustomerService customerService = DepedencyInjector.getInstance(CustomerService.class);

            UpsertOrderFrameView frame = new UpsertOrderFrameView(id, () -> {
                showOrders(null, selectedStatus);
            });

            new UpsertOrderController(frame, serviceService, customerService, this.orderService);
            frame.setVisible(true);
        });
    }

    private void showOrders(Integer id, String status) {
        List<OrderItem> orders = orderService.findAllOrders(id, status);

        DefaultTableModel model = (DefaultTableModel) view.getViewTable().getModel();
        model.setRowCount(0);

        for (OrderItem order : orders) {
            int orderId = order.getId();
            String customerName = order.getCustomerName();
            String serviceName = order.getServiceName();
            float weight = order.getWeight();
            String totalPrice = "Rp. " + String.valueOf(order.getPrice());
            String orderStatus = order.getStatus();
            String createdAt = DateUtil.timestampToDateString(order.getCreatedAt());
            String updatedAt = DateUtil.timestampToDateString(order.getDoneAt());

            model.addRow(new Object[]{
                orderId, customerName, serviceName, weight, totalPrice, orderStatus, createdAt, updatedAt
            });
        }
    }

    private void setupStatusComboBox() {
        List<String> items = new ArrayList<>();

        items.add(Status.ON_PROSSES);
        items.add(Status.DONE);

        for (String item : items) {
            view.getStatusComboBox().addItem(item);
        }

        view.getStatusComboBox().setSelectedItem(Status.ON_PROSSES);

        view.getStatusComboBox().addActionListener(e -> {
            selectedStatus = (String) view.getStatusComboBox().getSelectedItem();
            showOrders(null, selectedStatus);
            System.out.println("Selected Status: " + selectedStatus);
        });
    }

}
