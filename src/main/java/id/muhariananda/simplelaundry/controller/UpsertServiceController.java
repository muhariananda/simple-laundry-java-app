/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.controller;

import id.muhariananda.simplelaundry.entity.Service;
import id.muhariananda.simplelaundry.service.ServiceService;
import id.muhariananda.simplelaundry.view.UpsertServiceFrameView;

/**
 *
 * @author muhariananda
 */
public class UpsertServiceController {

    private final UpsertServiceFrameView view;
    private final ServiceService serviceHandler;

    public UpsertServiceController(UpsertServiceFrameView view, ServiceService serviceHandler) {
        this.view = view;
        this.serviceHandler = serviceHandler;

        loadService();
        setUpView();
    }

    private void setUpView() {
        view.getSaveButton().addActionListener((e) -> {
            String id = view.getIdTextField().getText();
            String name = view.getNameTextField().getText();
            String price = view.getPriceTextField().getText();

            boolean isNotValid = name.trim().isEmpty() && price.trim().isEmpty();

            if (isNotValid) {
                return;
            }

            if (id.isEmpty()) {
                serviceHandler.addService(name, Double.parseDouble(price));
            } else {
                serviceHandler.updateService(Integer.parseInt(id), name, Double.parseDouble(price));
            }
            
            view.getRefreshCallback().run();
            view.dispose();
        });
    }

    private void loadService() {
        String id = view.getIdTextField().getText();

        if (id == null || id.isEmpty()) {
            return;
        }

        Service service = serviceHandler.getService(Integer.parseInt(id));

        if (service != null) {
            String name = service.getName();
            String pricePerKg = Double.toString(service.getPricePerKg());

            view.getNameTextField().setText(name);
            view.getPriceTextField().setText(pricePerKg);
        }

    }

}
