package org.example.productservice.services;


import org.example.productservice.dtos.FakeStoreProductDto;
import org.example.productservice.exceptions.ProductAlreadyExistsException;
import org.example.productservice.exceptions.ProductNotFoundException;
import org.example.productservice.models.Category;
import org.example.productservice.models.Product;
import org.example.productservice.thirdPartyApis.FakeStoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeProductService")
public class FakeStoreProductServiceImpl implements ProductService{
    private final FakeStoreClient fakeStoreClient;

    @Autowired
    public FakeStoreProductServiceImpl(FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }
    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProducts = fakeStoreClient.getAllProducts();
        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProduct: fakeStoreProducts) {
            products.add(convertFakeStoreProductToProduct(fakeStoreProduct));
        }

        return products;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {

        FakeStoreProductDto fakeStoreProduct = fakeStoreClient.getProductById(id);

        if (fakeStoreProduct == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return convertFakeStoreProductToProduct(fakeStoreProduct);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeStoreProduct(product);
        FakeStoreProductDto fakeStoreProduct = fakeStoreClient.addProduct(fakeStoreProductDto);

        return convertFakeStoreProductToProduct(fakeStoreProduct);
    }

    @Override
    public Product deleteProductById(Long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreClient.deleteProductById(id);
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("product with id = " + id + " doesn't exist");
        }
        return convertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    public Product updateProductById(Product product) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreClient.updateProductById(convertProductToFakeStoreProduct(product));
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("product with id = " + product.getId() + " doesn't exist");
        }
        return convertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    private FakeStoreProductDto convertProductToFakeStoreProduct(Product product) {
        FakeStoreProductDto fakeStoreProduct = new FakeStoreProductDto();
        fakeStoreProduct.setTitle(product.getTitle());
        fakeStoreProduct.setDescription(product.getDescription());
        fakeStoreProduct.setPrice(product.getPrice());
        fakeStoreProduct.setId(product.getId());
        fakeStoreProduct.setCategory(product.getCategory().getName());
        return fakeStoreProduct;
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProduct) {
        Product product = new Product();
        Category category = new Category();
        product.setId(fakeStoreProduct.getId());
        product.setTitle(fakeStoreProduct.getTitle());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setPrice(fakeStoreProduct.getPrice());
        category.setName(fakeStoreProduct.getCategory());
        product.setCategory(category);

        return product;
    }
}
