package com.order.orderservice.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is an Order Entity class
 * @author 0031BS744
 * @version 1.0
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class OrderItem {
	
	@Id
	@Column(value="id")
	private int id;
	@Column(value="item_name")
	private String itemName;
	@Column(value="address")
	private String address;
	@Column(value="phnnum")
	private String phnnum;

}
