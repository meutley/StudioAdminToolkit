package com.meutley.studioadmintoolkit.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

    private static final int PRODUCT_ID = 1;
    
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        target = new ProductServiceImpl(
            modelMapper,
            productRepository
        );

        when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(new ProductDto());
        when(modelMapper.map(any(ProductDto.class), eq(Product.class))).thenReturn(new Product());
        when(modelMapper.map(any(List.class), eq(ProductDto[].class))).thenReturn(new ProductDto[] {});
        List<Product> defaultEntities = new ArrayList<>();
        defaultEntities.add(new Product());
        when(productRepository.findAll()).thenReturn(defaultEntities);
        when(productRepository.findById(any(int.class))).thenReturn(Optional.of(new Product()));
        when(productRepository.save(any(Product.class))).thenReturn(new Product());
    }

    @Test
    public void createShouldMapDtoToEntity() {
        target.create(new ProductDto());

        verify(modelMapper, times(1)).map(any(ProductDto.class), eq(Product.class));
    }

    @Test
    public void createWhenCalledShouldCallProductRepositorySave() {
        target.create(new ProductDto());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void createWhenCalledShouldMapEntityToDto() {
        target.create(new ProductDto());

        verify(modelMapper, times(1)).map(any(Product.class), eq(ProductDto.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void editWhenIdNotFoundThrowsEntityNotFoundException() {
        when(productRepository.findById(any(int.class))).thenReturn(Optional.empty());

        target.edit(new ProductDto());
    }

    @Test
    public void editWhenIdFoundCallsProductRepositorySave() {
        when(productRepository.findById(any(int.class))).thenReturn(Optional.of(new Product()));

        target.edit(new ProductDto());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void editWhenIdFoundMapsEntityToDto() {
        when(productRepository.findById(any(int.class))).thenReturn(Optional.of(new Product()));

        target.edit(new ProductDto());

        verify(modelMapper, times(1)).map(any(Product.class), eq(ProductDto.class));
    }

    @Test
    public void getAllShouldCallProductRepositoryFindAll() {
        target.getAll();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getAllShouldMapEntityCollectionToDtoArray() {
        target.getAll();

        verify(modelMapper, times(1)).map(any(List.class), eq(ProductDto[].class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdWhenNotFoundThrowsEntityNotFoundException() {
        when(productRepository.findById(any(int.class))).thenReturn(Optional.empty());

        target.getById(PRODUCT_ID);
    }

    @Test
    public void getByIdWhenCalledShouldCallProductRepositoryFindById() {
        when(productRepository.findById(any(int.class))).thenReturn(Optional.of(new Product()));
        
        target.getById(PRODUCT_ID);

        verify(productRepository, times(1)).findById(PRODUCT_ID);
    }

    @Test
    public void getByIdWhenFoundMapsEntityToDto() {
        when(productRepository.findById(any(int.class))).thenReturn(Optional.of(new Product()));

        target.getById(PRODUCT_ID);

        verify(modelMapper, times(1)).map(any(Product.class), eq(ProductDto.class));
    }
    
}