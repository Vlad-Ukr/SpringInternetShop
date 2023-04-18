package com.shop.service;

import com.shop.model.Category;

import java.util.List;

public interface CategoryService {

    Category find(int id);

    List<Category> findAll();
}
