/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.repository;

import id.muhariananda.simplelaundry.entity.Service;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public interface ServiceRepository {

    void add(Service service);

    Service get(int id);

    boolean update(Service service);

    boolean remove(int id);

    List<Service> findAll(String nameQuery);
}
