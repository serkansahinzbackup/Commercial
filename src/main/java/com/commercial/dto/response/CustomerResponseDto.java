package com.commercial.dto.response;

import com.commercial.repository.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerResponseDto {

    private Long id;

    private String name;

    private Cart cart;
}
