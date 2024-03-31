package com.commercial.mapper;

import com.commercial.dto.request.CustomerRequestDto;
import com.commercial.dto.response.CustomerResponseDto;
import com.commercial.repository.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICustomerMapper {

    ICustomerMapper INSTANCE = Mappers.getMapper(ICustomerMapper.class);

        Customer toCustomer(CustomerRequestDto customerRequestDto);

        CustomerResponseDto toCustomerResponseDto(Customer customer);

}
