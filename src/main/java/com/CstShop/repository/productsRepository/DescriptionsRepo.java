package com.CstShop.repository.productsRepository;

import com.CstShop.entity.products.Descriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionsRepo extends JpaRepository<Descriptions, Long> {
}
