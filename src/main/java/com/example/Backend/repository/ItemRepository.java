package com.example.Backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{

    List<Item> findByIsDeletedFalse();

    
} 
