package com.driver;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class OrderRepository {

    Map<String,Order> orderMap;
    Map<String,DeliveryPartner> deliveryPartnerMap;
    Map<Order,String>  assignedOrderMap;

    public OrderRepository(Map<String, Order> orderMap, Map<String, DeliveryPartner> deliveryPartnerMap, Map<Order, String> assignedOrderMap) {
        this.orderMap = orderMap;
        this.deliveryPartnerMap = deliveryPartnerMap;
        this.assignedOrderMap = assignedOrderMap;
    }

    public String addOrder(Order order) {
        String id = order.getId();
        if(orderMap.containsKey(id) == false){
            return orderMap.put(order.getId(),order) + "";
        }
        return  null;
    }

    public String addPartner(String partnerId) {
        if(deliveryPartnerMap.containsKey(partnerId) == false){
            return deliveryPartnerMap.put(partnerId,new DeliveryPartner(partnerId)) + " ";
        }
        return  null;

    }

    public Order getOrderById(String orderId) {
        if (orderMap.containsKey(orderId)){
            return  orderMap.get(orderId);
        }
        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if(deliveryPartnerMap.containsKey(partnerId)){
            return deliveryPartnerMap.get(partnerId);
        }
        return  null;
    }

    public String addOrderPartnerPair(String orderId, String partnerId) {

        if(assignedOrderMap.containsKey(getOrderById(orderId)) == false){
            return assignedOrderMap.put(getOrderById(orderId),partnerId);
        }
        return  null;
    }
}
