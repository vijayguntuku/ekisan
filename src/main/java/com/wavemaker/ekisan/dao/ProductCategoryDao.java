package com.wavemaker.ekisan.dao;

import java.util.List;

import com.wavemaker.ekisan.dto.ProductCategory;

public interface ProductCategoryDao {

     List<ProductCategory> findAllProductCategories();

}
