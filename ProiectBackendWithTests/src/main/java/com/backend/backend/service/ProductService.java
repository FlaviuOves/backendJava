package com.backend.backend.service;

import com.backend.backend.aop.LogExecutionTime;
import com.backend.backend.aop.LogParameters;
import com.backend.backend.dto.ProductDto;
import com.backend.backend.exception.product.ProductNotFoundException;
import com.backend.backend.model.ProductEntity;
import com.backend.backend.repository.ProductRepository;
import com.backend.backend.util.EntityToDtoConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDto saveProduct(ProductEntity product)
    {
        ProductEntity productEntity = productRepository.save(Objects.requireNonNull(product));
        return EntityToDtoConvertor.convertTo(productEntity);
    }

    @Transactional
    public ProductDto updateProduct(ProductEntity productUpdated,Long id)
    {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isPresent()) {
            BeanUtils.copyProperties(productUpdated, product.get());
            product.get().setId(id);
            productRepository.save(product.get());
            return EntityToDtoConvertor.convertTo(product.get());
        }
        else {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
    }

    @LogExecutionTime
    @LogParameters
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts()
    {
        List<ProductEntity> productEntities = productRepository.findAll();
        if(productEntities.isEmpty())
        {
            throw new ProductNotFoundException("No product was found!");
        }
        List<ProductDto> products = productEntities.stream().map(EntityToDtoConvertor::convertTo).collect(Collectors.toList());
        return products;
    }

    @LogExecutionTime
    @LogParameters
    @Transactional(readOnly = true)
    public ProductDto getProduct(Long id)
    {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isPresent())
        {
            return EntityToDtoConvertor.convertTo(product.get());
        }
        else
        {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

    }

    @Transactional
    public void deleteProduct(Long id)
    {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isPresent())
        {
            productRepository.deleteById(id);
        }
        else
        {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
    }

}
