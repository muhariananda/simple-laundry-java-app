/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package id.muhariananda.simplelaundry;

import id.muhariananda.simplelaundry.view.IncomePanelView;
import id.muhariananda.simplelaundry.view.OrderPanelView;
import id.muhariananda.simplelaundry.view.ServicePanelView;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author muhariananda
 */
public class SimpleLaundry {

    public static void main(String[] args) {

        List<JPanel> panels = new ArrayList<>();

        panels.add(DepedencyInjector.getInstance(OrderPanelView.class));
        panels.add(DepedencyInjector.getInstance(ServicePanelView.class));
        panels.add(DepedencyInjector.getInstance(IncomePanelView.class));

        java.awt.EventQueue.invokeLater(() -> {
            HomeLaundryFrame frame = new HomeLaundryFrame(panels);

            frame.pack();
            frame.setVisible(true);
        });
    }
}
