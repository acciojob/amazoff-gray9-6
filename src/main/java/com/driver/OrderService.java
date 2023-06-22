package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
