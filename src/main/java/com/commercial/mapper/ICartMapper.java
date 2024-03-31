package com.commercial.mapper;

import com.commercial.dto.request.CartRequestDto;
import com.commercial.dto.response.CartResponseDto;
import com.commercial.repository.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICartMapper {
    ICartMapper INSTANCE = Mappers.getMapper(ICartMapper.class);

    Cart toCart(CartRequestDto cartRequestDto);
    CartRequestDto tocartDto(Cart cart);
    CartResponseDto toCartResponseDto(Cart cart);
}
