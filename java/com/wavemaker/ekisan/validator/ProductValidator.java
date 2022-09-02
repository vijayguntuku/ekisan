package com.wavemaker.ekisan.validator;

import com.wavemaker.ekisan.dto.ErrorHolder;
import com.wavemaker.ekisan.dto.Product;

public class ProductValidator {

public ErrorHolder validate(ErrorHolder errorHolder, Product product){
    errorHolder = new ErrorHolder();
    if(null == product.getName())
        errorHolder.addError("name", product.getName(), "Name is mandatory.");

    return errorHolder;
}

}
