package com.backend.backend.product;

import com.backend.backend.dto.ProductDto;
import com.backend.backend.exception.product.ProductNotFoundException;
import com.backend.backend.model.ProductEntity;
import com.backend.backend.repository.ProductRepository;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.service.ProductService;
import com.backend.backend.service.UserService;
import com.backend.backend.util.EntityToDtoConvertor;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    private ProductEntity p1,p2;
    private ProductDto dto1,dto2;

    @BeforeEach
    public void init() {
         p1 = new ProductEntity(1l,"cirese","organice",34.7);
         p2 = new ProductEntity(2l,"bluza","roz",50.7);
         dto1 = EntityToDtoConvertor.convertTo(p1);
         dto2 = EntityToDtoConvertor.convertTo(p2);
    }

    @Test
    void getProductsTestSuccess(){
        List<ProductEntity> products = Arrays.asList(p1,p2);
        when(productRepository.findAll()).thenReturn(products);
        List<ProductDto> response = productService.getProducts();
        assertEquals(products.size(),response.size());
    }

    @Test
    void getProductsTestFailed(){
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ProductNotFoundException.class, ()-> productService.getProducts());
    }

    @Test
    void saveProductTest(){
        when(productRepository.save(any(ProductEntity.class))).thenReturn(p1);
        assertEquals(dto1,productService.saveProduct(p1));
    }

    @Test
    void updateProductTestSuccess(){
        ProductEntity p3 = new ProductEntity(1l,"cirese","organice",44.7);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(p1));
        assertEquals(EntityToDtoConvertor.convertTo(p3),productService.updateProduct(p3, 1l));
    }

    @Test
    void updateProductTestFailed(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, ()-> productService.updateProduct(p1,1l));
    }

    @Test
    void getProductTestSuccess(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(p1));
        assertEquals(dto1,productService.getProduct(1l));
    }

    @Test
    void getProductTestFailed(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(1l));
    }

    @Test
    void deleteProductTest(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(p1));
        assertDoesNotThrow(()->productService.deleteProduct(1l));
    }


}
