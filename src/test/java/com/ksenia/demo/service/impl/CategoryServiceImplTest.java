package com.ksenia.demo.service.impl;

import com.ksenia.demo.model.Category;
import com.ksenia.demo.model.User;
import com.ksenia.demo.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void addCategory()
    {
        Category category = new Category();
        category.setId(1);
        category.setName(Mockito.anyString());
        category.setProductTypes(Collections.emptySet());
        categoryService.addCategory(category);
        Mockito.doReturn(category).
                when(categoryRepository)
                .getOne(category.getId());
        Category addedCategory = categoryService.getCategoryById(category.getId());
        Assert.assertEquals(category, addedCategory);
    }

    @Test
    void getCategoryByName() {
        Category category = new Category();
        category.setId(12);
        Mockito.doReturn(category)
                .when(categoryRepository)
                .findCategoryByName("Clothes");

        Category categoryFromDb = categoryService.getCategoryByName("Clothes");

        Assert.assertNotNull(categoryFromDb);
    }


}