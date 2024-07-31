package com.search.searchapp.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.searchapp.models.Product;
import com.search.searchapp.models.Sku;
import com.search.searchapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataLoaderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Value("${elasticsearch.index.name}")
    private String indexName;

    @Value("${app.filter.enabled}")
    private boolean filterEnabled;

    @Value("${app.filter.color}")
    private String filterColor;

    @Value("${app.filter.available}")
    private boolean filterAvailable;

    @Autowired
    private ObjectMapper objectMapper;

    public void loadDataToElasticsearch() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            List<Sku> filteredSkus = product.getSkus();
            if (filterEnabled) {
                filteredSkus = filteredSkus.stream()
                        .filter(sku -> sku.getColor().equalsIgnoreCase(filterColor) && sku.getAvailable().equals(filterAvailable))
                        .collect(Collectors.toList());
            }
            product.setSkus(filteredSkus);

            try {
                IndexRequest<Product> indexRequest = IndexRequest.of(i -> i
                        .index(indexName)
                        .id(product.getId().toString())
                        .document(product)
                );

                elasticsearchClient.index(indexRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
