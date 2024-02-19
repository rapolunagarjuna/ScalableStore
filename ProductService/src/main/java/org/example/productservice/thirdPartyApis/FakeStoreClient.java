package org.example.productservice.thirdPartyApis;

import org.example.productservice.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeStoreClient {

    private final String BASE_URL = "https://fakestoreapi.com/products/";

    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductDto[] getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForEntity(BASE_URL , FakeStoreProductDto[].class).getBody();
    }

    public FakeStoreProductDto getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForEntity(BASE_URL + id , FakeStoreProductDto.class).getBody();
    }

    public FakeStoreProductDto deleteProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        return restTemplate.execute(BASE_URL + id, HttpMethod.DELETE, requestCallback, responseExtractor).getBody();
    }

    public FakeStoreProductDto addProduct(FakeStoreProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return  restTemplate.postForEntity(BASE_URL, product, FakeStoreProductDto.class).getBody();
    }

    public FakeStoreProductDto updateProductById(FakeStoreProductDto productDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(productDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        return restTemplate.execute(BASE_URL + productDto.getId(), HttpMethod.PUT, requestCallback, responseExtractor).getBody();
    }

}
