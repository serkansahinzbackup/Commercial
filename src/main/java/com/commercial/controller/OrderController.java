package com.commercial.controller;

import com.commercial.dto.request.OrderRequestDto;
import com.commercial.dto.response.OrderResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    //PlaceOrder
    //GetOrderForCode
    //GetAllOrdersForCustomer

    @PostMapping("/place_order")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.placeOrder(orderRequestDto));

    }


}
