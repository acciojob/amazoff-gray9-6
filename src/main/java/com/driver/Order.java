package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order() {
    }

    public Order(String id, String deliveryTime) {
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String[]time = deliveryTime.split(":");
        this.deliveryTime = (Integer.parseInt(time[0])*60) + Integer.parseInt(time[1]);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public static int getDeliverTimeInInt(String deliveryTime){
        String[]time = deliveryTime.split(":");
         int deliveryTime_In_Int = (Integer.parseInt(time[0])*60) + Integer.parseInt(time[1]);

         return deliveryTime_In_Int;
    }
    public static String getDeliveryTimeAsString(int time){
        int hrs = time/60;
        int mins = time % 60;
        String hrString = "";
        String minString = "";
        if(hrs < 10) hrString = "0" + hrs;
        else hrString = "" + hrs;

        if(mins < 10) minString = "0" + mins;
        else minString = "" + mins;

        return hrString + ":" + minString;
    }





    @Override
    public String toString(){
        return "Order{" +
                "id='" + id + '\'' +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
