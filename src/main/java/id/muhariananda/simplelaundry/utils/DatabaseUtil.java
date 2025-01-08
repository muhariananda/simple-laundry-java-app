/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author muhariananda
 */
public class DatabaseUtil {

    private static HikariDataSource hikariDataSource;

    static {
        HikariConfig configuration = new HikariConfig();
        configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuration.setUsername("root");
        configuration.setPassword("");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/simple_laundry_db?serverTimezone=Asia/Jakarta");

        configuration.setMaximumPoolSize(10);
        configuration.setMinimumIdle(5);
        configuration.setIdleTimeout(60_000);
        configuration.setMaxLifetime(60 * 60 * 1000);

        hikariDataSource = new HikariDataSource(configuration);
    }
    
    public static HikariDataSource getDataSource() {
        return hikariDataSource;
    }
}
