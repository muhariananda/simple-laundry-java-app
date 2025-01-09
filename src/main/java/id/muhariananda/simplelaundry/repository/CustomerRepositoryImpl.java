/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.repository;

import id.muhariananda.simplelaundry.entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author muhariananda
 */
public class CustomerRepositoryImpl implements CustomerRepository {

    private final DataSource dataSource;

    public CustomerRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO customers(name, contact) VALUES(?, ?)";
        Customer newCustomer = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getContact());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);

                        newCustomer = new Customer();
                        newCustomer.setId(generatedId);
                        newCustomer.setName(customer.getName());
                        newCustomer.setContact(customer.getContact());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return newCustomer;
    }

    @Override
    public Customer get(Integer id) {
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customer = new Customer();

                    customer.setId(resultSet.getInt("id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setContact(resultSet.getString("contact"));

                    return customer;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return customer;
    }

    @Override
    public List<Customer> search(String query) {
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM customers WHERE name LIKE ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + query + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Customer customer = new Customer();

                    customer.setId(resultSet.getInt("id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setContact(resultSet.getString("contact"));

                    customers.add(customer);
                }
            }

            return customers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println(customers);
        return customers;
    }

}
