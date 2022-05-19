package com.theverygroup.products.repository;

import com.theverygroup.products.dto.Product;
import com.theverygroup.products.util.ProductDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> products;

    @Autowired
    public ProductRepository() {
        products = ProductDataUtils.loadAll();
    }

    public List<Product> findAll() {
        return products;
    }
}