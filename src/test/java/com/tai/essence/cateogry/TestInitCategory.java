package com.tai.essence.cateogry;

import com.tai.essence.entity.Brand;
import com.tai.essence.entity.Category;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TestInitCategory {
    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    public void testInsertBrand() throws DuplicateNameException {
        Category category01 = new Category("Shoes",null,1);
        Category category02 = new Category("Accessory",null,1);
        Category category03 = new Category("Nike Shoes",category01,2);
        Category category04 = new Category("Adidas Shoes",category01,2);
        Category category05 = new Category("Backpack",category02,2);

        categoryRepository.saveAll(List.of(category01, category02, category03, category04, category05));

    }
}
