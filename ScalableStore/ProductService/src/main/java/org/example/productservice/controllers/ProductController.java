package org.example.productservice.controllers;


import org.example.productservice.exceptions.ProductAlreadyExistsException;
import org.example.productservice.exceptions.ProductNotFoundException;
import org.example.productservice.models.Product;
import org.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    ProductService productService;

    /*
    * three types of injections,
    * Field injection: not recommended, if you have so many beans, it will be difficult to understand
    * Constructor injection
    * Setter injection:
    * */


    // We need to add Qualifier when we have multiple implementation, and this helps us to distinguish between them
    @Autowired
    public ProductController(@Qualifier("FakeProductService") ProductService productService) {
        this.productService = productService;
    }


    /*
    readability, null pointer exception
    */
//    @Autowired
//    public void setProductService(ProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public Product deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.deleteProductById(id);
    }

    @PutMapping("/")
    public Product updateProductById(@RequestBody Product product) throws ProductNotFoundException {
        return productService.updateProductById(product);
    }

}
