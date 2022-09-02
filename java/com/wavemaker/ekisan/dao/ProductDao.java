package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Product;

import java.util.List;

public interface ProductDao {

    Product findProductByID(int id, String status);

    List<Product> findAllProducts();

    boolean saveOrUpdate(Product product);

    boolean deleteProduct(int id);
}
