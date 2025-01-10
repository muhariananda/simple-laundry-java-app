/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.muhariananda.simplelaundry.controller;

import id.muhariananda.simplelaundry.service.OrderService;
import id.muhariananda.simplelaundry.utils.DateUtil;
import id.muhariananda.simplelaundry.view.IncomePanelView;
import java.util.Date;

/**
 *
 * @author muhariananda
 */
public final class IncomeController {

    private final IncomePanelView view;
    private final OrderService orderService;

    public IncomeController(IncomePanelView view, OrderService orderService) {
        this.view = view;
        this.orderService = orderService;

        setupView();
    }

    void setupView() {
        setupIncomeLabel();
        view.getEnterButton().addChangeListener((e) -> {
            Date selectedDate = (Date) view.getDateSpinner().getValue();
            String formattedDate = DateUtil.formatDateToString(selectedDate);

            double income = orderService.getTotalIncomeByDate(formattedDate);
            String stringIncome = "Rp. " + String.valueOf(income);

            view.getIncomeLabel().setText(stringIncome);
        });
    }

    private void setupIncomeLabel() {
        Date currentDate = new Date();
        String formattedDate = DateUtil.formatDateToString(currentDate);

        double income = orderService.getTotalIncomeByDate(formattedDate);
        String stringIncome = "Rp. " + String.valueOf(income);

        view.getIncomeLabel().setText(stringIncome);
    }
}
