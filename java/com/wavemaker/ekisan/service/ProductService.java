package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.dto.Response;

public interface ProductService {
    Response findProduct(int id, String status);

    Response findAllProducts();

    Response saveOrUpdate(Product product);

    Response deleteProduct(int id);
}
