package com.commercial.service;

import com.commercial.dto.request.OrderRequestDto;
import com.commercial.dto.response.OrderResponseDetailDto;
import com.commercial.dto.response.OrderResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.mapper.IOrderMapper;
import com.commercial.repository.ICustomerRepository;
import com.commercial.repository.IOrderRepository;
import com.commercial.repository.entity.Cart;
import com.commercial.repository.entity.CartItem;
import com.commercial.repository.entity.Customer;
import com.commercial.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Cart cart = customer.getCart();
        List<CartItem> cartItemList = new ArrayList<>();
        for (int i = 0; i < customer.getCart().getCartItems().size(); i++) {
            cartItemList.add(customer.getCart().getCartItems().get(i));
        }

        UUID code = UUID.randomUUID();
        String shortUUID = code.toString().replace("-", "").substring(0, 8);

        Order order = Order.builder().
                cartItems(cartItemList).
                totalPrice(cart.getTotalPrice()).
                customer(customer).code(shortUUID)
        .build();

       order = orderRepository.save(order);

        cartService.emptyCartByID(cart.getId());

        return IOrderMapper.INSTANCE.toOrderResponseDto(order);
    }

    public OrderResponseDto getOrderForCode(String code) throws ResourceNotFoundException {
        Order order = orderRepository.findByCode(code).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        return IOrderMapper.INSTANCE.toOrderResponseDto(order);
    }

    public List<OrderResponseDetailDto> getOrderDetailForCode(String code) throws ResourceNotFoundException {
        Order order = orderRepository.findByCode(code).orElseThrow(()-> new ResourceNotFoundException("Order not found"));

        List<OrderResponseDetailDto> orderResponseDetailDtoList = new ArrayList<>();
        for (int i = 0; i < order.getCartItems().size(); i++) {

            OrderResponseDetailDto orderResponseDetailDto = OrderResponseDetailDto.builder().
                    currentPrice(order.getCartItems().get(i).getProduct().getPrice()).
                    previousPrice(order.getCartItems().get(i).getPrice()).
                    productName(order.getCartItems().get(i).getProduct().getName()).
                    purchasedDate(order.getCreationDate())
            .build();

            orderResponseDetailDtoList.add(orderResponseDetailDto);
        }

        return orderResponseDetailDtoList;
    }

    public List<OrderResponseDto> getAllOrdersForCustomer(Long customerId) throws ResourceNotFoundException{

        List<Order> orderList = orderRepository.findAll();
        orderList = orderList.stream().filter(a-> a.getCustomer().getId().equals(customerId)).toList();

        List<OrderResponseDto> orderResponseDtoList=new ArrayList<>();

        for (int i = 0; i < orderList.size(); i++) {
            OrderResponseDto orderResponseDto = IOrderMapper.INSTANCE.toOrderResponseDto(orderList.get(i));
            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }
}
