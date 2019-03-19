package com.meutley.studioadmintoolkit.product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(
        ModelMapper modelMapper,
        ProductRepository productRepository
    ) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto create(ProductDto product) {
        Product entity = modelMapper.map(product, Product.class);
        BigDecimal unitPrice = product.getIsBillable()
            ? product.getUnitPrice()
            : BigDecimal.ZERO;
        entity.setUnitPrice(unitPrice);
        return modelMapper.map(this.productRepository.save(entity), ProductDto.class);
    }

    @Override
    public ProductDto edit(ProductDto product) {
        Optional<Product> entity = this.productRepository.findById(product.getId());
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(product.getId());
        }
        
        entity.get().setName(product.getName());
        entity.get().setDescription(product.getDescription());
        entity.get().setIsBillable(product.getIsBillable());
        BigDecimal unitPrice = product.getIsBillable()
            ? product.getUnitPrice()
            : BigDecimal.ZERO;
        entity.get().setUnitPrice(unitPrice);

        return modelMapper.map(this.productRepository.save(entity.get()), ProductDto.class);
    }
    
    @Override
    public List<ProductDto> getAll() {
        return Arrays.asList(
            modelMapper.map(
                this.productRepository.findAll(), ProductDto[].class));
    }

    @Override
    public ProductDto getById(int id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        return modelMapper.map(product.get(), ProductDto.class);
    }

}