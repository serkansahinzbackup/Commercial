package com.commercial.dto.response;


import com.commercial.repository.entity.CartItem;
import com.commercial.repository.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderResponseDto {

    private Long id;

    private Customer customer;

    private List<CartItem> cartItems;

    private Double totalPrice;

    private UUID code;
}
