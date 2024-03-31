package com.commercial.repository.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity // todo buna gerek var gibi sıfırdan yapıyor gibi yap
public class PriceHistory {

    private Long id;
//    @ManyToOne
    private Product product;
    private double price;
    private int quantity; //todo quantity ile stock aynı mı
    private LocalDateTime updatedAt;
}
