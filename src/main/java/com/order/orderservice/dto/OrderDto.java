package com.order.orderservice.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * This class is an Order Data Access Object which holds the values provided by the user
 * @author 0031BS744
 * @version 1.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class OrderDto {

	@Id
	private int id;
	@NotEmpty(message = "please enter itemName")
	private String itemName;
	@NotEmpty(message = "please enter address")
	private String address;
	@NotEmpty(message = "please enter phnnum")
	private String phnnum;
}
