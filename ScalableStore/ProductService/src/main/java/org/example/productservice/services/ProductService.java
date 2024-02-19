package org.example.productservice.services;

import org.example.productservice.exceptions.ProductAlreadyExistsException;
import org.example.productservice.exceptions.ProductNotFoundException;
import org.example.productservice.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();

    public Product getProductById(Long id) throws ProductNotFoundException;

    public Product createProduct(Product product);

    public Product deleteProductById(Long id) throws ProductNotFoundException;

    public Product updateProductById(Product product) throws ProductNotFoundException;

}
