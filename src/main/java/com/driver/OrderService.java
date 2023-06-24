package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public String addOrder(Order order) {
        return orderRepository.addOrder(order);
    }


    public String  addPartner(String partnerId) {
        return orderRepository.addPartner(partnerId);
    }

    public Order getOrderById(String orderId) {
        return  orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return  orderRepository.getPartnerById(partnerId);
    }

    public String addOrderPartnerPair(String orderId, String partnerId) {
        return  orderRepository.addOrderPartnerPair(orderId,partnerId);
    }


    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return  orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return  orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }
}
