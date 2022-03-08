package ru.clevertec.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.clevertec.ecxeption.ProductNotFoundException;
import ru.clevertec.entity.Producer;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.ProductRepository;
import ru.clevertec.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductService productService;
    private List<Product> products;

    private Product lemon = new Product(1L, "Lemon", 12.3, new Producer(1L, "USA"));
    private Product milk = new Product(2L, "Milk", 19.23, new Producer(2L, "Ukraine"));
    private Product lime = new Product(3L, "Lime", 0.99, new Producer(1L, "USA"));
    private Product juice = new Product(4L, "Juice", 2.33, new Producer(1L, "USA"));


    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
        products = new ArrayList<>();
        fillProducts(products);
    }

    @Test
    void shouldNotBeEmptyWhenGetAll() {
        Mockito.doReturn(products).when(productRepository).getAll();
        List<Product> productList = productService.getAll();
        assertThat(productList).isNotEmpty();
    }

    @Test
    void shouldAddProductAndReturnTrue() {
        Product newProduct = new Product(123L, "Potato", 0.19, new Producer(1L, "USA"));
        Mockito.doReturn(newProduct.getId()).when(productRepository).addProduct(newProduct);
        Long productId = productService.addProduct(newProduct);
        assertEquals(newProduct.getId(), productId);
    }

    @Test
    void shouldReturnProductWhenGetProduct() {
        Mockito.doReturn(Optional.of(lemon)).when(productRepository).findById(lemon.getId());
        Optional<Product> maybeProduct = productService.getProduct(lemon.getId());
        assertEquals(maybeProduct, Optional.of(lemon));
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenGetProduct() {
        Mockito.doThrow(ProductNotFoundException.class).when(productRepository).findById(0L);
        assertThrows(ProductNotFoundException.class, ()-> productService.getProduct(0L));
    }

    @Test
    void shouldUpdateProductAndReturnTrue() {
        Mockito.doReturn(true).when(productRepository).updateProduct(lemon);
        lemon.setName("lemon2");
        boolean isUpdate = productService.updateProduct(lemon);
        assertTrue(isUpdate);
    }

    @Test
    void shouldNotUpdateProductAndReturnFalse() {
        Product emptyProduct = new Product();
        Mockito.doReturn(false).when(productRepository).updateProduct(emptyProduct);
        boolean isUpdate = productService.updateProduct(emptyProduct);
        assertFalse(isUpdate);
    }

    @Test
    void shouldRemoveProductAndReturnTrue() {
        Mockito.doReturn(true).when(productRepository).removeProduct(lemon.getId());
        boolean isRemove = productService.removeProduct(lemon.getId());
        assertTrue(isRemove);
    }

    @Test
    void shouldNotRemoveProductAndReturnFalse() {
        Product emptyProduct = new Product();
        Mockito.doReturn(false).when(productRepository).removeProduct(emptyProduct.getId());
        boolean isRemove = productService.removeProduct(emptyProduct.getId());
        assertFalse(isRemove);
    }

    private void fillProducts(List<Product> products) {
        products.add(lemon);
        products.add(milk);
        products.add(lime);
        products.add(juice);
    }
}