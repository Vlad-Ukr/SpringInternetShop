package com.shop.service.impl;

import com.shop.model.Category;
import com.shop.repository.CategoryRepository;
import com.shop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category find(int id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
