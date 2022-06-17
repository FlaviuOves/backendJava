package com.backend.backend.controller;

import com.backend.backend.dto.ProductDto;
import com.backend.backend.model.ProductEntity;
import com.backend.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-api")

public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity getAllProducts()
    {
        var products = productService.getProducts();
        return ResponseEntity.ok().body(products);
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager')")
    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductEntity productEntity)
    {
        ProductDto product = productService.saveProduct(productEntity);
        return ResponseEntity.ok().body(product);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductEntity productEntity,@PathVariable Long id)
    {
        ProductDto product = productService.updateProduct(productEntity,id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto>getProduct(@PathVariable Long id)
    {
       ProductDto product = productService.getProduct(id);
       return ResponseEntity.ok().body(product);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
