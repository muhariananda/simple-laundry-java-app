/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.repository;

import id.muhariananda.simplelaundry.entity.Service;
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
public class ServiceRepositoryImpl implements ServiceRepository {

    private final DataSource dataSource;

    public ServiceRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Service service) {
        String sql = "INSERT INTO services(name, price_per_kg) VALUES(? , ?)";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, service.getName());
                statement.setDouble(2, service.getPricePerKg());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Service service) {
        String sql = "UPDATE services SET name = ?, price_per_kg = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, service.getName());
            statement.setDouble(2, service.getPricePerKg());
            statement.setInt(3, service.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remove(int id) {
        String sql = "DELETE FROM services WHERE id = ?";

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
    public Service get(int id) {
        Service service = null;
        String sql = "SELECT * FROM services WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    service = new Service();

                    service.setId(resultSet.getInt("id"));
                    service.setName(resultSet.getString("name"));
                    service.setPricePerKg(resultSet.getDouble("price_per_kg"));
                    service.setCreatedAt(resultSet.getTimestamp("created_at"));

                    return service;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return service;
    }
    
    @Override
    public List<Service> findAll(String nameQuery) {
        List<Service> services = new ArrayList<>();
        
        String sql = "SELECT * FROM services";
        boolean isQueryEmpty = nameQuery == null || nameQuery.trim().isEmpty();
        
        if (!isQueryEmpty) {
            sql = "SELECT * FROM services WHERE name LIKE ?";
        }

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            
            if (!isQueryEmpty) {
                statement.setString(1, "%" + nameQuery.trim() + "%");
            }
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Service service = new Service();

                    service.setId(resultSet.getInt("id"));
                    service.setName(resultSet.getString("name"));
                    service.setPricePerKg(resultSet.getDouble("price_per_kg"));
                    service.setCreatedAt(resultSet.getTimestamp("created_at"));

                    services.add(service);
                }
            }

            return services;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return services;
    }

}
