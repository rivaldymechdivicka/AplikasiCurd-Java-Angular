package com.example.Backend.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Backend.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
}
