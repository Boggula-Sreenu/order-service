package com.order.orderservice.util;


import com.order.orderservice.entity.OrderItem;

public class OrderUtil {
	
	 private static OrderItem order;


	    public static OrderItem getOrders(){

	    	order = new OrderItem();
	    	order.setId(1);
	    	order.setItemName("test");
	    	order.setPhnnum("user");
	    	order.setAddress("addr1");
	        return order;



	    }
}
