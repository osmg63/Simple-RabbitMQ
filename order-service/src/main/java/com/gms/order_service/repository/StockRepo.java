package com.gms.order_service.repository;

import com.gms.order_service.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock, Integer> {
    Object findByProduct_Id(int productId);
}
