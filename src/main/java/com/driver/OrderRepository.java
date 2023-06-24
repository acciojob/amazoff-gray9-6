package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    //orderId,order
    public HashMap<String,Order> orderHashMap;
    //partnerId,deliverypartner
    public HashMap<String,DeliveryPartner> deliveryPartnerHashMap;
    //partnerId,List of orders
    public HashMap<String,List<String>> partnerOrderHashMap;
    //order , deliverypartner     which order is deilvered by which deliverypartner
    public HashMap<String, String> orderPartnerPairMap;

    public OrderRepository(){
        this.orderHashMap = new HashMap<>();
        this.deliveryPartnerHashMap = new HashMap<>();
        this.partnerOrderHashMap = new HashMap<>();
        this.orderPartnerPairMap = new HashMap<>();
    }

    public void addOrder(Order order) {
        orderHashMap.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnerHashMap.put(partnerId,new DeliveryPartner(partnerId));
    }

    public Order getOrderById(String orderId) {
        return orderHashMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Integer count = null;
        List<String> list = partnerOrderHashMap.get(partnerId);
        if(partnerOrderHashMap.containsKey((partnerId)))
        {
            count = partnerOrderHashMap.get(partnerId).size();
        }
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orders = new ArrayList<>();
        if(partnerOrderHashMap.containsKey(partnerId))
        {
            orders = partnerOrderHashMap.get(partnerId);
        }
        return orders;
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();
        for(String orderId : orderHashMap.keySet())
        {
            list.add(orderId);
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
//        int count = 0;
//        for(Order order : orderHashMap.values())
//        {
//            String orderId = order.getId();
//            if(!orderPartnerPairMap.containsKey(orderId)) count++;
//        }
//        return count;
        return orderHashMap.size() - orderPartnerPairMap.size();
    }

    public void deletePartnerById(String partnerId) {
        //delete order assigned to that delivery partner from partnerorderhashmap
        List<String> orderList = partnerOrderHashMap.get(partnerId);
        for(String orderId : orderList)
        {
            orderPartnerPairMap.remove(orderId);
        }
        //delete it from partnerOrderhashmap
        partnerOrderHashMap.remove(partnerId);
        //delete order partner pair from delivery partner hashmap
        deliveryPartnerHashMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {

        orderHashMap.remove(orderId);
        String partnerId = orderPartnerPairMap.get(orderId);
        orderPartnerPairMap.remove(orderId);
        partnerOrderHashMap.get(partnerId).remove(orderId);
        deliveryPartnerHashMap.get(partnerId).setNumberOfOrders(partnerOrderHashMap.get(partnerId).size());
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> orderlistOfPartner = new ArrayList<>();

        if(partnerOrderHashMap.containsKey(partnerId)){
            orderlistOfPartner = partnerOrderHashMap.get(partnerId);
        }
        orderlistOfPartner.add(orderId);
        partnerOrderHashMap.put(partnerId, orderlistOfPartner);

        //update orderPartnerPairMap
        orderPartnerPairMap.put(orderId, partnerId);

        //update no of orders by the delivery Partner
        DeliveryPartner deliveryPartner =deliveryPartnerHashMap.get(partnerId);
        deliveryPartner.setNumberOfOrders(orderlistOfPartner.size());
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count = 0;
        int tyme = Order.getDeliveryTimeAsInt(time);
        List<String> listOfOrders = partnerOrderHashMap.get(partnerId);
        for(String orderId : listOfOrders)
        {
            if(orderHashMap.get(orderId).getDeliveryTime() > tyme) count++;
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastDeliveryTime = 0;
        List<String> orderlist = partnerOrderHashMap.get(partnerId);
        for(String orderId : orderlist){
            lastDeliveryTime = Math.max(lastDeliveryTime,orderHashMap.get(orderId).getDeliveryTime());
        }
        return Order.getDeliveryTimeAsString(lastDeliveryTime);
    }
}