package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Address;
import com.wavemaker.ekisan.dto.Product;

import java.util.List;

public interface AddressDao {

    Address findAddressByID(int id);

    boolean saveOrUpdate(Address address);

    boolean deleteAddress(int id);

}
