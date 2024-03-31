package com.commercial.controller;

import com.commercial.dto.request.CartRequestDto;
import com.commercial.dto.request.ProductRequestDto;
import com.commercial.dto.response.CartResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/get_cart_by_id"+"/{id}")
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable Long id) throws ResourceNotFoundException {

        return ResponseEntity.ok(cartService.getCartById(id));
    }
//    @PostMapping("create_cart")
//    public ResponseEntity<CartResponseDto> createCart(@RequestBody CartRequestDto cartDto) throws ResourceNotFoundException{
//        return ResponseEntity.ok(cartService.createCart(cartDto));
//    }

    @PostMapping("create_empty_cart"+"/{customerId}")
    public ResponseEntity<CartResponseDto> createEmptyCart(@PathVariable Long customerId) throws ResourceNotFoundException{
        return ResponseEntity.ok(cartService.createEmptyCart(customerId));
    }

    @PutMapping("add_product_to_cart")
    public ResponseEntity<CartResponseDto> addProductToCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer quantity) throws ResourceNotFoundException{
        return ResponseEntity.ok(cartService.addProductToCart(cartId, productId, quantity));
    }
    @PutMapping("remove_product_from_cart")
    public ResponseEntity<CartResponseDto> removeProductFromCart(@RequestParam Long cartId, @RequestParam Long cartItemId) throws ResourceNotFoundException{
        return ResponseEntity.ok(cartService.removeProductFromCart(cartId, cartItemId));
    }

    @PutMapping("/update_cart_by_id"+"/{id}")
    public ResponseEntity<CartResponseDto> updateCartByID(@PathVariable Long id, @RequestBody CartRequestDto cartRequestDto) throws ResourceNotFoundException{
        return ResponseEntity.ok(cartService.updateCartById(id, cartRequestDto));
    }

    @PutMapping("/empty_by_cartId"+"/{id}")
    public ResponseEntity<String> emptyCartByID(Long cartId) throws ResourceNotFoundException{
        return ResponseEntity.ok(cartService.emptyCartByID(cartId));
    }
}
