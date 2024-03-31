package com.commercial.dto.response;


import com.commercial.repository.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CartResponseDto {

    private Long id;

    private List<CartItem> cartItems;

    private Double totalPrice;
}
