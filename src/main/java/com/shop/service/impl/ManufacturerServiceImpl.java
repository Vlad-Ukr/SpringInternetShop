package com.shop.service.impl;

import com.shop.model.Manufacturer;
import com.shop.repository.ManufacturerRepository;
import com.shop.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer find(int id) {
        return manufacturerRepository.findById(id).get();
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }
}
