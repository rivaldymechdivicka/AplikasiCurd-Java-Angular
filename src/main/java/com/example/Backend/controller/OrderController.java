package com.example.Backend.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Backend.dto.CreateOrderRequest;
import com.example.Backend.dto.UpdateOrderRequest;
import com.example.Backend.entity.Order;
import com.example.Backend.service.OrderService;



@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId,
            @RequestBody UpdateOrderRequest updateRequest) {
        if (!orderService.getOrderById(orderId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Order updatedOrder = orderService.updateOrder(orderId, updateRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        if (!orderService.getOrderById(orderId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
