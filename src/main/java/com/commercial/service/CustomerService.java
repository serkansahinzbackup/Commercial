package com.commercial.service;

import com.commercial.dto.request.CustomerRequestDto;
import com.commercial.dto.response.CustomerResponseDto;
import com.commercial.mapper.ICustomerMapper;
import com.commercial.repository.ICustomerRepository;
import com.commercial.repository.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ICustomerRepository customerRepository;
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = ICustomerMapper.INSTANCE.toCustomer(customerRequestDto);

        customer=customerRepository.save(customer);

        return ICustomerMapper.INSTANCE.toCustomerResponseDto(customer);
    }
}
