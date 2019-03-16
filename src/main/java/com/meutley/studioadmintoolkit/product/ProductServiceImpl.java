package com.meutley.studioadmintoolkit.product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
        Product entity = this.productRepository.getOne(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setIsBillable(product.getIsBillable());
        BigDecimal unitPrice = product.getIsBillable()
            ? product.getUnitPrice()
            : BigDecimal.ZERO;
        entity.setUnitPrice(unitPrice);

        return modelMapper.map(this.productRepository.save(entity), ProductDto.class);
    }
    
    @Override
    public List<ProductDto> getAll() {
        return Arrays.asList(
            modelMapper.map(
                this.productRepository.findAll(), ProductDto[].class));
    }

    @Override
    public ProductDto getById(int id) {
        return modelMapper.map(this.productRepository.getOne(id), ProductDto.class);
    }

}