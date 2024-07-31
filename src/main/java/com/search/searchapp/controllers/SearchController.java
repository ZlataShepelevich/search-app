package com.search.searchapp.controllers;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.search.searchapp.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @GetMapping
    public List<Product> searchProducts(@RequestParam String query) {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("products")
                .query(q -> q
                        .multiMatch(m -> m
                                .query(query)
                                .fields("name", "description", "skus.skuCode", "skus.color")
                        )
                )
        );

        try {
            SearchResponse<Product> searchResponse = elasticsearchClient.search(searchRequest, Product.class);
            return searchResponse.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
