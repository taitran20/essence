package com.tai.essence.repository;

import com.tai.essence.entity.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    @Query("select p from Product p " +
            "where (p.category.id = :categoryId or :categoryId is null or p.category.parentCategory.id = :categoryId or p.category.parentCategory.parentCategory.id = :categoryId) "+
            "and ((:minPrice is null and :maxPrice is null) or (p.discountPrice between :minPrice and :maxPrice)) "+
            "and (:minDiscount is null or p.discountPercent >= :minDiscount) "+
            "order by "+
            "case when :sort = 'price_low' then p.discountPrice end ASC, "+
            "case when :sort = 'price_high' then p.discountPrice end DESC")
    List<Product> filterProducts(@Param("category") Long categoryId,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice,
                                 @Param("minDiscount") Double minDiscount,
                                 @Param("sort") String sort);
}
