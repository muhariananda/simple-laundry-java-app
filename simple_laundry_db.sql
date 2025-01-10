/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  muhariananda
 * Created: Jan 10, 2025
 */

CREATE DATABASE simple_laundry_db;
USE simple_laundry_db;

CREATE TABLE services (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	price_per_kg DECIMAL(10, 2) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

CREATE TABLE customers (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	contact VARCHAR(15) NOT NULL,
	UNIQUE (contact)
) ENGINE = InnoDB;

CREATE TABLE orders (
	id INT AUTO_INCREMENT PRIMARY KEY,
	customer_id INT NOT NULL,
	service_id INT NOT NULL,
	weight_kg DECIMAL(5, 2) NOT NULL,
	total_price DECIMAL(10, 2) NOT NULL,
	status ENUM('Dalam Proses', 'Selesai', 'Dibatalkan') DEFAULT 'Dalam Proses',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
	FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE INDEX idx_contact ON customers(contact);
CREATE INDEX idx_created_at ON orders(created_at);