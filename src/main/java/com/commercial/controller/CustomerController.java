package com.commercial.controller;

import com.commercial.dto.request.CustomerRequestDto;
import com.commercial.dto.response.CustomerResponseDto;
import com.commercial.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    @PostMapping("/add_customer")
    public ResponseEntity<CustomerResponseDto> addCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        return ResponseEntity.ok(customerService.addCustomer(customerRequestDto));
    }

}
