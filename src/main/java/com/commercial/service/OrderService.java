package com.commercial.service;

import com.commercial.dto.request.OrderRequestDto;
import com.commercial.dto.response.OrderResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.mapper.IOrderMapper;
import com.commercial.repository.ICartRepository;
import com.commercial.repository.ICustomerRepository;
import com.commercial.repository.IOrderRepository;
import com.commercial.repository.entity.Cart;
import com.commercial.repository.entity.CartItem;
import com.commercial.repository.entity.Customer;
import com.commercial.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;
    private final CartService cartService;

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(orderRequestDto.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("Customer not found"));

        System.out.println(customer);
        Cart cart = customer.getCart();
        List<CartItem> cartItemList = cart.getCartItems();
        System.out.println(cartItemList);


        System.out.println(cart);
        UUID code = UUID.randomUUID();

        Order order = Order.builder().
                cartItems(cart.getCartItems()).
                totalPrice(cart.getTotalPrice()).
                customer(customer).code(code)
        .build();

        System.out.println(order);
       order = orderRepository.save(order);

        cartService.emptyCartByID(cart.getId());

        return IOrderMapper.INSTANCE.toOrderResponseDto(order);

    }
}
