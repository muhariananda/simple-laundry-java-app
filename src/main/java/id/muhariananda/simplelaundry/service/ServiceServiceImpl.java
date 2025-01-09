/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.service;

import id.muhariananda.simplelaundry.entity.Service;
import id.muhariananda.simplelaundry.repository.ServiceRepository;
import java.util.List;

/**
 *
 * @author muhariananda
 */
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void addService(String name, double price) {
        Service service = new Service();
        service.setName(name);
        service.setPricePerKg(price);

        serviceRepository.add(service);
    }

    @Override
    public Service getService(int id) {
        return serviceRepository.get(id);
    }

    @Override
    public void updateService(int id, String name, double price) {
        Service service = new Service();
        service.setId(id);
        service.setName(name);
        service.setPricePerKg(price);

        boolean isUpdated = serviceRepository.update(service);

        if (isUpdated) {
            System.out.println("Layanan berhasil diubah");
        } else {
            System.out.println("Layanan gagal diubah");
        }
    }

    @Override
    public void deleteService(int id) {
        boolean isDeleted = serviceRepository.remove(id);

        if (isDeleted) {
            System.out.println("Layanan berhasil dihapus");
        } else {
            System.out.println("Layanan gagal dihapus");
        }
    }

    @Override
    public List<Service> searchService(String name) {
        return serviceRepository.findAll(name);
    }
    
}
