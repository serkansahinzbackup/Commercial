package com.commercial.service;

import com.commercial.dto.request.ProductRequestDto;
import com.commercial.dto.response.ProductResponseDto;
import com.commercial.exception.ResourceNotFoundException;
import com.commercial.mapper.IProductMapper;
import com.commercial.repository.IProductRepository;
import com.commercial.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;

    public List<ProductResponseDto> findAllProducts() {
        List<Product> productList= productRepository.findAll();

        List<ProductResponseDto> productResponseDtoList=new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {

            ProductResponseDto productResponseDto = IProductMapper.INSTANCE.toProductResponseDto(productList.get(i));
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    public ProductResponseDto findProductById(Long id) throws ResourceNotFoundException {

         Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("PRODUCT NOT FOUND"));

        return IProductMapper.INSTANCE.toProductResponseDto(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

        Product product = IProductMapper.INSTANCE.toProduct(productRequestDto);

         productRepository.save(product);
        return IProductMapper.INSTANCE.toProductResponseDto(product);
    }

    public ProductResponseDto updateProductById(Long id, ProductRequestDto productRequestDto) throws ResourceNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found ID: "+ id));

        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setStock(productRequestDto.getStock());

         productRepository.save(product);
        return IProductMapper.INSTANCE.toProductResponseDto(product);
    }

    public String deleteProductById(Long id) throws ResourceNotFoundException {
        Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found ID: " + id));

         productRepository.deleteById(product.getId());
        return product.getId() + " " + product.getName() + " has been deleted";

    }


}
