/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.controller;

import id.muhariananda.simplelaundry.entity.Service;
import id.muhariananda.simplelaundry.service.ServiceService;
import id.muhariananda.simplelaundry.view.ServicePanelView;
import id.muhariananda.simplelaundry.view.UpsertServiceFrameView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author muhariananda
 */
public class ServiceController {

    private final ServicePanelView view;
    private final ServiceService serviceHandler;

    public ServiceController(ServicePanelView view, ServiceService serviceHandler) {
        this.view = view;
        this.serviceHandler = serviceHandler;

        loadServices(null);
        setupView();
    }

    private void setupView() {

        view.getRefreshButton().addActionListener(e -> {
            loadServices(null);
            view.getSearchTextField().setText("");
        });

        view.getSearchButton().addActionListener((e) -> {
            String query = view.getSearchTextField().getText();
            loadServices(query);
        });

        view.getAddButton().addActionListener((e) -> {
            UpsertServiceFrameView frame = new UpsertServiceFrameView(null, () -> {
                loadServices(null);
            });

            new UpsertServiceController(frame, serviceHandler);
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

            UpsertServiceFrameView frame = new UpsertServiceFrameView(id, () -> {
                loadServices(null);
            });

            new UpsertServiceController(frame, serviceHandler);
            frame.setVisible(true);

            loadServices(null);
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

            serviceHandler.deleteService(id);

            loadServices(null);
        });
    }

    private void loadServices(String query) {
        List<Service> services = serviceHandler.searchService(query);

        DefaultTableModel model = (DefaultTableModel) view.getViewTable().getModel();
        model.setRowCount(0);

        for (Service service : services) {
            int id = service.getId();
            String name = service.getName();
            double pricePerKg = service.getPricePerKg();

            String stringPrice = "Rp. " + String.valueOf(pricePerKg);

            model.addRow(new Object[]{id, name, stringPrice});
        }
    }
}
