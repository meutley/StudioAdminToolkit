package com.meutley.studioadmintoolkit.product;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto product);
    ProductDto edit(ProductDto product);
    List<ProductDto> getAll();
    ProductDto getById(int id);
    
}