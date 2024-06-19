package com.example.Backend.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.TransactionReport;
import com.example.Backend.entity.Order;
import com.example.Backend.repository.OrderRepository;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepository;

public byte[] generateTransactionReport(Date startDate, Date endDate) {
    try {
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        List<TransactionReport> transactionReports = convertOrdersToTransactionReports(orders);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactionReports);
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/templates/transactionReport.jrxml");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    } catch (JRException e) {
        e.printStackTrace();
        return null;
    }
}

private List<TransactionReport> convertOrdersToTransactionReports(List<Order> orders) {
    return orders.stream()
            .map(order -> new TransactionReport(order.getOrderDate(),
                    order.getCustomer().getCustomerName(), order.getItem().getItemName(),
                    order.getItem().getPrice(), order.getQuantity(), order.getTotalPrice()))
            .collect(Collectors.toList());
    }
}
