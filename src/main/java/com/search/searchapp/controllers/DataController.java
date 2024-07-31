package com.search.searchapp.controllers;

import com.search.searchapp.services.DataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataLoaderService dataLoaderService;

    @GetMapping("/load")
    public ResponseEntity<String> loadData() {
        dataLoaderService.loadDataToElasticsearch();
        return ResponseEntity.ok("Data loaded successfully");
    }
}
