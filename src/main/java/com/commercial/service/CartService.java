package com.commercial.service;

import com.commercial.dto.request.CartRequestDto;
import com.commercial.dto.response.CartResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.exception.StockException;
import com.commercial.mapper.ICartMapper;
import com.commercial.repository.ICartItemRepository;
import com.commercial.repository.ICartRepository;
import com.commercial.repository.ICustomerRepository;
import com.commercial.repository.IProductRepository;
import com.commercial.repository.entity.Cart;
import com.commercial.repository.entity.CartItem;
import com.commercial.repository.entity.Customer;
import com.commercial.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;
    public CartResponseDto getCartById(Long cartId) throws ResourceNotFoundException {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isEmpty()){
            throw new ResourceNotFoundException("cart not found");
        }
        return ICartMapper.INSTANCE.toCartResponseDto(cart.get());
    }

    public CartResponseDto createEmptyCart(Long customerId) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer not found"));

        if (customer.getCart()==null){
            Cart cart = new Cart();
            customer.setCart(cart);

            cart = cartRepository.save(cart);

            return ICartMapper.INSTANCE.toCartResponseDto(cart);
        } else {
            emptyCartByID(customer.getCart().getId());

            return ICartMapper.INSTANCE.toCartResponseDto(customer.getCart());
        }


    }

    public CartResponseDto addProductToCart(Long cartId, Long productId, Integer quantity) throws ResourceNotFoundException, StockException {

        Cart cart=cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found"));

        if(product.getStock()>=quantity){
            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
        } else {
            throw new StockException(product.getName()+" insufficient in the stock, remaining stock: " + product.getStock());
        }
        CartItem cartItem = CartItem.builder().product(product).quantity(quantity).price(product.getPrice()).build();

        cartItem = cartItemRepository.save(cartItem);

        cart.getCartItems().add(cartItem);

        double totalPrice=0.0;

        totalPrice = cart.getCartItems().stream()
                .mapToDouble(a -> (a.getQuantity() * a.getProduct().getPrice())).sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        return ICartMapper.INSTANCE.toCartResponseDto(cart);
    }

    public CartResponseDto removeProductFromCart(Long cartId, Long cartItemId) throws ResourceNotFoundException {

        Cart cart=cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not found"));//todo tümünü gez yalış yazmış olabilirsin

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new ResourceNotFoundException("CartItem not found"));

        cart.getCartItems().remove(cartItem);

        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(a -> (a.getQuantity() * a.getProduct().getPrice())).sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        return ICartMapper.INSTANCE.toCartResponseDto(cart);
    }


    public CartResponseDto updateCartById(Long id, CartRequestDto cartRequestDto) throws ResourceNotFoundException{
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()){
            throw new RuntimeException("cart not found");
        }

        List<CartItem> cartItemList = new ArrayList<>();
        for (int i = 0; i < cartRequestDto.getCartItemIds().size(); i++) {
            CartItem cartItem =cartItemRepository.
                    findById(cartRequestDto.getCartItemIds().get(i)).
                    orElseThrow(()->new ResourceNotFoundException("CartItem not found"));

            cartItemList.add(cartItem);
        }

        double totalPrice=0.0;

        cart.get().setCartItems(cartItemList);

        totalPrice = cartItemList.stream()
                .mapToDouble(a -> (a.getQuantity() * a.getProduct().getPrice())).sum();
        cart.get().setTotalPrice(totalPrice);
        cartRepository.save(cart.get());

        return ICartMapper.INSTANCE.toCartResponseDto(cart.get());
    }

    public CartResponseDto updateCartItem(Long cartItemId, Integer quantity) throws ResourceNotFoundException, StockException{

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("CartItem not found"));

        Product product = cartItem.getProduct();

        Integer stock=cartItem.getQuantity() + cartItem.getProduct().getStock();

        if (stock >= quantity){
            product.setStock(stock-quantity);
            productRepository.save(product);
        } else {
            throw new StockException(product.getName()+" insufficient in the stock, remaining stock: " + stock);
        }

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        Cart cart = cartRepository.findAll().stream().
                filter(a->a.getCartItems().
                        contains(cartItem)).
                findFirst().get();

        double totalPrice=0.0;

        totalPrice = cart.getCartItems().stream()
                .mapToDouble(a -> (a.getQuantity() * a.getProduct().getPrice())).sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        return ICartMapper.INSTANCE.toCartResponseDto(cart);
    }


    public String emptyCartByID(Long cartId) throws ResourceNotFoundException{
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()){
            throw new ResourceNotFoundException("Cart not found");
        }

        List<CartItem> cartItemList = new ArrayList<>();

        cart.get().setCartItems(cartItemList);

        cart.get().setTotalPrice(0.0);

        cartRepository.save(cart.get());

        return cart.get().getId()+ " id numbered Cart has been emptied successfully";
    }


}
