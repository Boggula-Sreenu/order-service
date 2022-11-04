package com.order.orderservice.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.order.orderservice.dto.OrderDto;
import com.order.orderservice.entity.OrderItem;
import com.order.orderservice.exception.DataNotFoundException;
import com.order.orderservice.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest(OrderController.class)
public class OrderControllerTest {

	@Autowired
	WebTestClient webTestClient;
	@MockBean
	OrderService orderService;

	@DisplayName("Get all Order details : Happy flow")
	@Test
	void findAll() {

		OrderItem order = OrderItem.builder().id(1).itemName("test").address("test").phnnum("868").build();
		given(orderService.findAll()).willReturn(Flux.just(order));
		webTestClient.get().uri(uriBuilder -> uriBuilder.path("/order/").build()).exchange().expectStatus().isOk();

	}

	@DisplayName("Fetch Order record by Id")
	@Test
	void findAllByID() {
		OrderDto orderMono = OrderDto.builder().id(1).itemName("Hyde").address("hyd").phnnum("133").build();
		when(orderService.getOrderById(1)).thenReturn(Mono.just(orderMono));
	        Mono<OrderDto> responseMono= webTestClient.get()
	                .uri(builder -> builder.path("/order/{id}")
	                        .build("1")).exchange().expectStatus().isOk().returnResult(OrderDto.class)
	                .getResponseBody().single();
	        StepVerifier.create(responseMono).expectSubscription()
	                .expectNextMatches(a->"Hyde".equals(a.getItemName()) && "hyd".equals(a.getAddress()))
	                .verifyComplete();
	}


	@DisplayName("Update the record through OrderID")
	@Test
	void updateByID() {
		OrderDto orderMono = OrderDto.builder().id(1).itemName("Hyde").address("hyd").phnnum("133").build();

		when(orderService.updateOrder(orderMono, 1)).thenReturn(Mono.just(orderMono));

		webTestClient.put().uri(builder -> builder.path("/order/update/{id}").build("1"))
				.body(Mono.just(orderMono), OrderDto.class).exchange().expectStatus().isOk();

	}
	@DisplayName("Save the Order details")
	@Test
	void save() {

		OrderItem orderMono = OrderItem.builder().id(1).itemName("Hyde").address("hyd").phnnum("133").build();
		OrderDto orderDtoMono = OrderDto.builder().id(1).itemName("Hyde").address("hyd").phnnum("133").build();

		when(orderService.saveOrder(orderMono)).thenReturn(Mono.just(orderMono));

		webTestClient.post().uri(builder -> builder.path("/order/save").build()).body(Mono.just(orderMono),OrderDto.class).exchange().expectStatus().isOk();
		
	}
	@DisplayName("Delete the Order Details")
	@Test
	void delete() {

		when(orderService.deleteOrderById(1)).thenReturn(Mono.empty());

		webTestClient.delete().uri(builder -> builder.path("/order/delete/{id}").build("1")).exchange().expectStatus()
				.isOk();
	}
	
	@Test
	public void getOrdByID_Not_Found_Test() {

		when(orderService.getOrderById(1)).thenReturn(Mono.empty());

		webTestClient.delete().uri("/order/deleteOrder/{id}", "1").exchange().expectStatus().isEqualTo(404);

	}
	

	@Test
	public void updateID_Not_Found_Test() {
		
		OrderDto orderMono = OrderDto.builder().id(1).itemName("Hyde").address("hyd").phnnum("133").build();
		when(orderService.updateOrder(orderMono, 1)).thenReturn(Mono.empty());

		webTestClient.put().uri("/order/updateOrder/{id}", "1").exchange().expectStatus().isEqualTo(404);

	}
	@Test
	public void delete_Not_Found_Test() {
		
		when(orderService.deleteOrderById(1)).thenReturn(Mono.empty());

		ResponseSpec response = webTestClient.delete().uri("/order/deleteOrder/{id}", "1").exchange();
		response.expectStatus().isEqualTo(404);

	}
	
	@Test
	public void save_Found_Test() {
		OrderItem orderMono = OrderItem.builder().id(1).itemName("Hyde").address("hyd").phnnum("").build();
		
		when(orderService.saveOrder(orderMono)).thenThrow(WebExchangeBindException.class);

		ResponseSpec response = webTestClient.delete().uri("/order/save", "1").exchange();
		response.expectStatus().isEqualTo(405);

	}
	
	
	
	
	 

}
