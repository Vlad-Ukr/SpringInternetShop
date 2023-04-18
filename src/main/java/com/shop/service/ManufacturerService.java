package com.shop.service;

import com.shop.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    Manufacturer find(int id);

    List<Manufacturer> findAll();
}
