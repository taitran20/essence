package com.tai.essence.brand;

import com.tai.essence.entity.Brand;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.repository.BrandRepository;
import com.tai.essence.service.brand.BrandService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;

import static org.mockito.Mockito.*;


@SpringBootTest
public class BrandServiceUnitTest {
    @Mock
    private BrandRepository brandRepository;
    @Autowired
    private BrandService brandService;

    @Test
    public void testInsertBrand() throws DuplicateNameException {
        Brand brand = new Brand("Adidas","This is Adidas","Adidas Url");

        when(brandRepository.save(brand)).thenReturn(brand);

        boolean result = brandService.insert(brand);
        assertEquals(result, true);
        //verify(brandRepository, times(1)).save(brand);
    }
}
