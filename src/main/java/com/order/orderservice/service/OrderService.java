package com.order.orderservice.service;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.orderservice.dto.OrderDto;
import com.order.orderservice.entity.OrderItem;
import com.order.orderservice.exception.DataNotFoundException;
import com.order.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This is an Order Service class which calls the repository methods to handle
 * the requests
 *
 * @author 0031BS744
 * @version 1.0
 * @see Service
 */
@RequiredArgsConstructor
@Service

public class OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;

	@Autowired
	 WebClient.Builder webClient;

	/**
	 * This returns all the Order details
	 *
	 * @return all the Order
	 */
	public Flux<OrderItem> findAll() {
		logger.info("Get all the order details");
		return orderRepository.findAll();
	}

	/**
	 * This returns the Order based on order id
	 *
	 * @param id of the Order
	 * @return the Order details with the given id
	 */

	public Mono<OrderDto> getOrderById(final int id) {
		logger.info("Get the order details through order id");
		return orderRepository.findById(id).switchIfEmpty(Mono.error(new DataNotFoundException()))
				.map(this::entityToDto);
	}

	public Mono<String> getEmployee(){
		return webClient.build().get()
				.uri("http://localhost:3000/employee/")
				.retrieve()
				.bodyToMono(String.class);

	}

	/**
	 * This saves the Order details to the database
	 * @param order of Order
	 * @return the Order with unique id
	 */
	public Mono<OrderItem> saveOrder(OrderItem order) {
		logger.info("save the order details");
			return orderRepository.save(order);
		
		

		
	
		 
	}

	/**
	 * This updates the Order with the passed id to the database
	 *
	 * @param id  of the Order
	 * @param orderDto Mono of OrderDto
	 * @return the Order with updated value
	 */
	public Mono<OrderDto> updateOrder(OrderDto orderDto, final int id) {
		logger.info("Update the order details through order id");
		return orderRepository.findById(id).switchIfEmpty(Mono.error(new DataNotFoundException()))
				.flatMap(e -> {
			e.setAddress(orderDto.getAddress() != null ? orderDto.getAddress() : e.getAddress());
			e.setItemName(orderDto.getItemName() != null ? orderDto.getItemName() : e.getItemName());
			e.setPhnnum(orderDto.getPhnnum() != null ? orderDto.getPhnnum() : e.getPhnnum());
			return orderRepository.save(e);

		}).map(this::entityToDto);
	}

	/**
	 * This deletes the Order with the passed id
	 *
	 * @param id of the Order
	 * @return Nothing
	 */
	public Mono<Void> deleteOrderById(int id) {
		logger.info("Delete the order details through order id");
		return orderRepository.findById(id).switchIfEmpty(Mono.error(new DataNotFoundException()))
				.flatMap(orderRepository::delete);
	}

	/**
	 * This converts the Order Entity to OrderDto
	 *
	 * @param Order object of Order Entity
	 * @return object of OrderDto
	 */
	public OrderDto entityToDto(OrderItem Order) {
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(Order, orderDto);
		return orderDto;
	}

	/**
	 * This converts the OrderDto to Order Entity
	 *
	 * @param orderDto object of OrderDto
	 * @return object of Order Entity
	 */
	public OrderItem dtoToEntity(OrderDto orderDto) {
		OrderItem order = new OrderItem();
		BeanUtils.copyProperties(orderDto, order);
		return order;
	}

	

}
