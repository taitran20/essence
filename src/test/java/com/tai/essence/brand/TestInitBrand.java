package com.tai.essence.brand;

import com.tai.essence.entity.Brand;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.repository.BrandRepository;
import com.tai.essence.service.brand.BrandService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TestInitBrand {
    @Autowired
    private BrandRepository brandRepository;
    @Test
    public void testInsertBrand() throws DuplicateNameException {
        Brand brand01 = new Brand("Adidas","This is Adidas","Adidas Url");
        Brand brand02 = new Brand("Nike","This is Nike","Nike Url");
        Brand brand03 = new Brand("Dior","This is Dior","Dior Url");

        //when(brandRepository.saveAll(List.of(brand01, brand02, brand03))).thenReturn(List.of(brand01, brand02, brand03));

        brandRepository.saveAll(List.of(brand01, brand02, brand03));
        //assertEquals(result, true);
        //verify(brandRepository, times(1)).save(brand);
    }
}
