/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.service;

import id.muhariananda.simplelaundry.entity.Order;
import id.muhariananda.simplelaundry.entity.OrderItem;
import id.muhariananda.simplelaundry.entity.Status;
import id.muhariananda.simplelaundry.repository.OrderRepository;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrder(int memberId, int serviceId, float weight, double totalPrice) {
        Order order = new Order();
        order.setMemberId(memberId);
        order.setServiceId(serviceId);
        order.setWeight(weight);
        order.setTotalPrice(totalPrice);

        orderRepository.add(order);
    }

    @Override
    public Order getOrder(Integer id) {
        return orderRepository.get(id);
    }

    @Override
    public void updateOrder(int orderId, int serviceId, float weight, double totalPrice) {
        Order order = new Order();
        order.setId(orderId);
        order.setServiceId(serviceId);
        order.setWeight(weight);
        order.setTotalPrice(totalPrice);

        boolean isUpdated = orderRepository.update(order);

        if (isUpdated) {
            System.out.println("Pesanan berhasil diubah");
        } else {
            System.out.println("Pesanan gagal diubah");
        }
    }

    @Override
    public void deleteOrder(Integer id) {
        boolean isDeleted = orderRepository.remove(id);

        if (isDeleted) {
            System.out.println("Pesanan berhasil dihapus");
        } else {
            System.out.println("Pesanan gagal dihapus");
        }
    }

    @Override
    public List<OrderItem> findAllOrders(Integer id, String status) {
        return orderRepository.findAll(id, status);
    }

    @Override
    public void updateStatusToDone(Integer id) {
        boolean isUpdated = orderRepository.updateStatus(id, Status.DONE);

        if (isUpdated) {
            System.out.println("Pesanan telah selesasi");
        } else {
            System.out.println("Gagal mengubah status");
        }
    }

    @Override
    public double getTotalIncomeByDate(String date) {
        return orderRepository.countIncome(date);
    }

}
