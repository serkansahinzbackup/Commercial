package com.commercial.controller;

import com.commercial.dto.request.OrderRequestDto;
import com.commercial.dto.response.OrderResponseDetailDto;
import com.commercial.dto.response.OrderResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place_order")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.placeOrder(orderRequestDto));

    }

    @GetMapping("/get_order_for_code" + "/{code}")
    public ResponseEntity<OrderResponseDto> getOrderForCode(@PathVariable String code) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.getOrderForCode(code));
    }

    @GetMapping("/get_order_detail_for_code" + "/{code}")
    public ResponseEntity<List<OrderResponseDetailDto>> getOrderDetailForCode(@PathVariable String code) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.getOrderDetailForCode(code));
    }



    @GetMapping("/get_all_orders_for_customer" + "/{customerId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersForCustomer(@PathVariable Long customerId) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.getAllOrdersForCustomer(customerId));
    }


}
