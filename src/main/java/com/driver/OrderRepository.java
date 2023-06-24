package com.driver;

import com.sun.source.doctree.SeeTree;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    static Map<String,Order> orderMap;
   static Map<String,DeliveryPartner> deliveryPartnerMap;
    static Map<String,String> orderPartnerMap;
    static Map<String, List<String>> partnertoOrderMap;

    // Constructor



    public OrderRepository(Map<String, Order> orderMap, Map<String, DeliveryPartner> deliveryPartnerMap,
                           Map<String, String> orderPartnerMap, Map<String, List<String>> partnertoOrderMap) {

        this.orderMap = orderMap;
        this.deliveryPartnerMap = deliveryPartnerMap;
        this.orderPartnerMap = orderPartnerMap;
        this.partnertoOrderMap = partnertoOrderMap;
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

        // check orderid and partnerid should be present in databases;
        if(orderMap.containsKey(orderId)== false || deliveryPartnerMap.containsKey(partnerId) == false){
            return  null;
        }

        // now update the partner - order map
        // first get all the orders that this partner have ,, if does not have any order, then it will
        // return new set,, and if it have the orderSet then it will return that, in either case we will add
        // our order to that empty set or the existing order set
        List<String> orders = partnertoOrderMap.getOrDefault(partnerId,new ArrayList<>());
        orders.add(orderId);
        partnertoOrderMap.put(partnerId,orders);

        //update no of orders by the delivery Partner
        DeliveryPartner deliveryPartner =deliveryPartnerMap.get(partnerId);
        deliveryPartner.setNumberOfOrders(orders.size());

        // check order pehle se hi kisi ko assign toh nahi hai
        // and update the order - partner map
         boolean order_id = orderPartnerMap.containsKey(orderId);
        if(order_id == false){
            orderPartnerMap.put(orderId,partnerId);
            return "New order-partner pair added successfully";
        }

        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
         Integer count = 0;

        if(partnertoOrderMap.containsKey(partnerId)){
            List<String> list =  partnertoOrderMap.get(partnerId);
           return list.size();
        }
        return count;


//        if(partnertoOrderMap.containsKey(partnerId)){
//           DeliveryPartner orderCount =  deliveryPartnerMap.get(partnerId);
//            count = orderCount.getNumberOfOrders();
//        }
//        return count;

    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String > list = new ArrayList<>();
        if(partnertoOrderMap.containsKey(partnerId)){
            list =  partnertoOrderMap.get(partnerId);
        }
        return list;
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();
        for(String order : orderMap.keySet()){
            list.add(order);
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders() {

        // getting the total orders
        int totalOrder = getAllOrders().size();

        // getting the assigned orders
        int assignedOrder = orderPartnerMap.size();

        // Total order - assigned order
        int unassignedOrder = totalOrder - assignedOrder;

        return unassignedOrder;

    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count = 0;
        int deliveryTime = Order.getDeliverTimeInInt(time);

        List<String> orderlist = partnertoOrderMap.get(partnerId);
        for(String orders : orderlist){

            int del_time = orderMap.get(orders).getDeliveryTime();
            if(del_time > deliveryTime){
                count++;
            }
        }
        return  count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastDeliverTime = 0;
        List<String> orders = partnertoOrderMap.get(partnerId);
        for(String orderId : orders){
            lastDeliverTime = Math.max(lastDeliverTime,orderMap.get(orderId).getDeliveryTime());
        }
        return Order.getDeliveryTimeAsString(lastDeliverTime) ;
    }

    public void deletePartnerById(String partnerId) {

        // delete partner from deliver partner map
       DeliveryPartner partner = getPartnerById(partnerId);
        deliveryPartnerMap.remove(partner);

        // delete partner from order partner map
        List<String> orders = getOrdersByPartnerId(partnerId);
        for (String order : orders){
            orderPartnerMap.remove(order,partnerId);
        }

        // delete partner from partner to order
        partnertoOrderMap.remove(partner);


    }



    public void deleteOrderById(String orderId) {

        // delete it from order map
        orderMap.remove(orderId);

        //delete it from order parner map
        String partner = orderPartnerMap.get(orderId);
        orderPartnerMap.remove(orderId);

        // remove it form partner order map
          List<String> orderList = partnertoOrderMap.get(partner);
          for(String order : orderList){
              if(order.equals(orderId)){
                  orderList.remove(order);
              }
          }

          DeliveryPartner deliveryPartner = deliveryPartnerMap.get(partner);
          deliveryPartner.setNumberOfOrders(orderList.size());

    }


}

