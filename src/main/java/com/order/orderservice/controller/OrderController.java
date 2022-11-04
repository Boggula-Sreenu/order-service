package com.order.orderservice.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.orderservice.dto.OrderDto;
import com.order.orderservice.entity.OrderItem;
import com.order.orderservice.service.OrderService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class is an Order Controller which handles the API requests from the client
 * @author 0031BS744
 * @version 1.0
 * @see RestController
 */
@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Validated
public class OrderController {

	
	private OrderService orderService;
	
	/**
     * Returns all the Orders details
     * @return all the Orders
     */

	@GetMapping("/")
	public Flux<OrderItem> findAll() {
		return orderService.findAll();
	}

	@GetMapping("/reactive")
	public Mono<String> getEmployee(){

		return orderService.getEmployee();
	}

	/**
     * This returns the Order based on order id
     * @param  id of the Order
     * @return the Order with the given id
     */
	
	@GetMapping("/{id}")
	public Mono<OrderDto> getOrderById(@PathVariable final int id) {

		return orderService.getOrderById(id);
	}

	 /**
     * This save the Order to the database
     * @param order of Mono OrderDto
     * @return the Order details with uninque id
     */
	@PostMapping("/save")
	public ResponseEntity<Mono<OrderItem>> saveOrder(@RequestBody @Valid OrderItem order) {
		
		
		Mono<OrderItem> orderitem = orderService.saveOrder(order);
		
		return new ResponseEntity(orderitem, HttpStatus.OK);
	}

	 /**
     * This deletes the Order with the passed order id
     * @param id of the Order
     * @return void
     */
	@DeleteMapping("/delete/{id}")
	public Mono<Void> deleteOrder(@PathVariable int id) {
		return orderService.deleteOrderById(id);
	}

	/**
     * This updates the Order with the passed id to the database
     * @param id  of the Order
     * @param orddto of the OrderDto
     * @return the Order with updated value
     */
	@PutMapping("/update/{id}")
	public Mono<OrderDto> updateOrder(@RequestBody OrderDto orddto, @PathVariable int id) {
		return orderService.updateOrder(orddto, id);
	}


}
