package com.commercial.controller;

import com.commercial.dto.request.ProductRequestDto;
import com.commercial.dto.response.ProductResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.repository.entity.Product;
import com.commercial.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    @GetMapping("/get_all_products")
    public ResponseEntity<List<ProductResponseDto>> findAllProducts(){

        return ResponseEntity.ok(productService.findAllProducts());

    }
    @GetMapping("/get_product_by_id"+"/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable Long id) throws ResourceNotFoundException{

        return ResponseEntity.ok(productService.findProductById(id));

    }

    @PostMapping("create_product")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productDto){
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PutMapping("update_product"+"/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable Long id, @RequestBody ProductRequestDto productDto) throws ResourceNotFoundException{
        return ResponseEntity.ok(productService.updateProductById(id, productDto));
    }

    @DeleteMapping("delete_product"+"/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) throws ResourceNotFoundException { // todo deletebyid yapılmalı mı
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

}
