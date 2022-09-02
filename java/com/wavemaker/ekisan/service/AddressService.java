package com.wavemaker.ekisan.service;

import com.wavemaker.ekisan.dto.Address;
import com.wavemaker.ekisan.dto.Response;

public interface AddressService {
    Response findAddressById(int id);


    Response saveOrUpdate(Address address);

    Response deleteAddress(int id);
}
