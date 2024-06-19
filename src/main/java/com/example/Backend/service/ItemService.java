package com.example.Backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.entity.Item;
import com.example.Backend.repository.ItemRepository;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems(){
        return itemRepository.findByIsDeletedFalse();
    }
    public Optional<Item>getItemById(Long itemId){
        return itemRepository.findById(itemId);
    }
    public Item createOrUpdateItem(Item item){
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        optionalItem.ifPresent(item -> {
            item.setIsDeleted(true);
            itemRepository.save(item);
        });
    }

    public void updateStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        item.setStock(item.getStock() + quantity);
        itemRepository.save(item);
    }
}
