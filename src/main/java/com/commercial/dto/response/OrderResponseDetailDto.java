package com.commercial.dto.response;

import com.commercial.repository.entity.CartItem;
import com.commercial.repository.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderResponseDetailDto {

    private String productName;

    private Double currentPrice;

    private Double previousPrice;

    private LocalDateTime purchasedDate;

}
