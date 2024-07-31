package com.search.searchapp.repositories;

import com.search.searchapp.models.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, Long> {}

