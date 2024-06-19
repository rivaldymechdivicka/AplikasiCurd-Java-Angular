package com.example.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Backend.entity.Item;
import com.example.Backend.service.ItemService;


@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.createOrUpdateItem(item);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @RequestBody Item item) {
        if (!itemService.getItemById(itemId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        item.setItemId(itemId);
        return ResponseEntity.ok(itemService.createOrUpdateItem(item));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        if (!itemService.getItemById(itemId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}/stock")
    public ResponseEntity<Item> updateStock(@PathVariable Long itemId, @RequestParam int quantity) {
        itemService.updateStock(itemId, quantity);
        return ResponseEntity.ok().build();
    }
}
