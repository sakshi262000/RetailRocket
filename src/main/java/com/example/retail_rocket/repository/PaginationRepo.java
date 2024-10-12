package com.example.retail_rocket.repository;

import com.example.retail_rocket.model.Products;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaginationRepo  extends PagingAndSortingRepository<Products, Long> {
}
