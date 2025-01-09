/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.repository;

import id.muhariananda.simplelaundry.entity.Order;
import id.muhariananda.simplelaundry.entity.OrderItem;
import id.muhariananda.simplelaundry.entity.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author muhariananda
 */
public class OrderRepositoryImpl implements OrderRepository {

    private final DataSource dataSource;

    public OrderRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Order order) {
        String sql = "INSERT INTO "
                + "orders(customer_id, service_id, weight_kg, total_price, status) "
                + "VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, order.getMemberId());
                statement.setInt(2, order.getServiceId());
                statement.setFloat(3, order.getWeight());
                statement.setDouble(4, order.getTotalPrice());
                statement.setString(5, Status.ON_PROSSES);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Order get(Integer id) {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order();

                    order.setId(resultSet.getInt("id"));
                    order.setMemberId(resultSet.getInt("customer_id"));
                    order.setServiceId(resultSet.getInt("service_id"));
                    order.setWeight(resultSet.getFloat("weight_kg"));
                    order.setTotalPrice(resultSet.getDouble("total_price"));
                    order.setStatus(resultSet.getString("status"));
                    order.setCreatedAt(resultSet.getTimestamp("created_at"));
                    order.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return order;
    }

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE orders SET "
                + "service_id = ?, weight_kg = ?, total_price = ? "
                + "WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, order.getServiceId());
            statement.setFloat(2, order.getWeight());
            statement.setDouble(3, order.getTotalPrice());
            statement.setInt(4, order.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(Integer id, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remove(Integer id) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsAffetcted = statement.executeUpdate();
            return rowsAffetcted > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<OrderItem> findAll(Integer id, String status) {
        List<OrderItem> orders = new ArrayList<>();

        String sql = "SELECT "
                + "o.id, "
                + "c.name AS customer_name, "
                + "s.name AS service_name, "
                + "o.weight_kg, "
                + "o.total_price, "
                + "o.status, "
                + "o.created_at, "
                + "o.updated_at "
                + "FROM orders o "
                + "JOIN customers c ON o.customer_id = c.id "
                + "JOIN services s ON o.service_id = s.id ";

        boolean firstCondition = true;

        if (id != null || status != null) {
            sql += " WHERE ";

            if (id != null) {
                sql += "o.id = ? ";
                firstCondition = false;
            }

            if (status != null) {
                if (!firstCondition) {
                    sql += "AND ";
                }
                sql += "o.status = ? ";
            }
        }

        sql += "ORDER BY o.created_at";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            int parameterIndex = 1;

            if (id != null) {
                statement.setInt(parameterIndex++, id);
            }

            if (status != null) {
                statement.setString(parameterIndex++, status);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem order = new OrderItem();

                    order.setId(resultSet.getInt("id"));
                    order.setCustomerName(resultSet.getString("customer_name"));
                    order.setServiceName(resultSet.getString("service_name"));
                    order.setWeight(resultSet.getFloat("weight_kg"));
                    order.setPrice(resultSet.getDouble("total_price"));
                    order.setStatus(resultSet.getString("status"));
                    order.setCreatedAt(resultSet.getTimestamp("created_at"));
                    order.setDoneAt(resultSet.getTimestamp("updated_at"));

                    orders.add(order);
                }
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    @Override
    public double countIncome(String date) {
        double income = 0;
        String sql = "SELECT SUM(total_price) AS income "
                + "FROM orders "
                + "WHERE status = 'Selesai' "
                + "AND DATE(updated_at) = ?";
        
        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, date);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    income = resultSet.getDouble("income");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return income;
    }
}
