package com.order.orderservice.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.order.orderservice.dto.OrderDto;
import com.order.orderservice.entity.OrderItem;
import com.order.orderservice.repository.OrderRepository;
import com.order.orderservice.util.OrderUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { OrderRepository.class })
public class OrderServiceTest {

	public OrderService orderService;
	@MockBean
	private OrderRepository orderRepository;

	@BeforeEach
	void setUp() {
		this.orderService = new OrderService(orderRepository);
	}

	@DisplayName("Get All Order details: Happy flow")
	@Test
	 void findAll() {
		OrderItem order = OrderUtil.getOrders();

		when(orderRepository.findAll()).thenReturn(Flux.just(order));

		StepVerifier.create(orderService.findAll()).expectNextMatches(ord -> "1".equals(ord.getId())).expectNextCount(0)
				.expectComplete().verify();
	}
	@DisplayName("Get Order By using OrderID: Happy flow")
	@Test
	 void getOrderByID() {
		OrderItem order = OrderUtil.getOrders();

		when(orderRepository.findAll()).thenReturn(Flux.just(order));

		when(orderRepository.findById(1)).thenReturn(Mono.just(order));

		StepVerifier.create(orderRepository.findAll()).expectNextMatches(emp -> {
			then(emp.getId()).isEqualTo("1");
			return true;
		}).expectNextCount(0).expectComplete().verify();
	}
	@DisplayName("Save Order: Happy flow")
	@Test
	 void saveOrder() {
		OrderItem order = OrderUtil.getOrders();

		when(orderRepository.findAll()).thenReturn(Flux.just(order));

		when(orderRepository.save(order)).thenReturn(Mono.just(order));
		Mono<OrderDto> ordDTO = Mono
				.just(OrderDto.builder().id(1).address("abc").itemName("test").phnnum("user").build());
		order.setAddress("abc");
		when(orderRepository.save(order)).thenReturn(Mono.just(order));

		StepVerifier.create(orderService.saveOrder(order))
		.expectNextMatches(ord -> "1".equals(ord.getId()))
		.expectNextCount(0).expectNextCount(0).expectComplete().verify();
	}

	@DisplayName("Update the Order details using OrderID: Happy flow")
	@Test
	void testUpdate() {
		OrderItem Order = OrderUtil.getOrders();
		when(orderRepository.findAll()).thenReturn(Flux.just(Order));
		when(orderRepository.findById(1)).thenReturn(Mono.just(Order));
		OrderDto empDTO = 
				OrderDto.builder().id(1).address("abc").itemName("test").phnnum("user").build();
		Order.setAddress("abc");
		when(orderRepository.save(Order)).thenReturn(Mono.just(Order));

		StepVerifier.create(orderService.updateOrder(empDTO, 1)).expectNextMatches(emp -> {
			then(emp.getAddress()).isEqualTo("abc");
			return true;
		}).expectComplete().verify();

	}
	
	

	/*
	 * @DisplayName("While try to get the Order details If Order ID is not found")
	 * 
	 * @Test public void getEmpByID_Not_Found_Test() {
	 * 
	 * when(orderService.deleteOrderById("1")).thenReturn(Mono.empty());
	 * 
	 * webTestClient.delete().uri("/order/{id}",
	 * "1").exchange().expectStatus().isEqualTo(404);
	 * 
	 * }
	 * 
	 * @DisplayName("While update If Order ID is not found")
	 * 
	 * @Test public void updateID_Not_Found_Test() {
	 * 
	 * when(orderService.deleteOrderById("1")).thenReturn(Mono.empty());
	 * 
	 * webTestClient.put().uri("/order/update/{id}",
	 * "1").exchange().expectStatus().isEqualTo(404);
	 * 
	 * }
	 */

}
