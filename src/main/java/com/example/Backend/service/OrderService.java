package com.example.Backend.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.CreateOrderRequest;
import com.example.Backend.dto.UpdateOrderRequest;
import com.example.Backend.entity.Customer;
import com.example.Backend.entity.Item;
import com.example.Backend.entity.Order;
import com.example.Backend.repository.CustomerRepository;
import com.example.Backend.repository.ItemRepository;
import com.example.Backend.repository.OrderRepository;



@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemRepository itemRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Order createOrder(CreateOrderRequest order) {
        Customer customer = customerRepository.findById(order.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Item item = itemRepository.findById(order.getItemId())
            .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        Order newOrder = new Order();

        int orderItem = order.getQuantity();

        int stock = item.getStock(); 
        int pengurangan = stock - orderItem;
        newOrder.setCustomer(customer);
        newOrder.setItem(item);
        newOrder.setQuantity(order.getQuantity());
        newOrder.setTotalPrice(order.getQuantity() * item.getPrice());
        newOrder.setOrderDate(new Date());
        customer.setLastOrder(new Date());
        customerRepository.save(customer);

        item.setStock(pengurangan);
        itemRepository.save(item);
        return orderRepository.save(newOrder);
    }

    public Order updateOrder(Long orderId, UpdateOrderRequest updateRequest) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Customer customer = customerRepository.findById(updateRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Item item = itemRepository.findById(updateRequest.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        existingOrder.setCustomer(customer);
        existingOrder.setItem(item);
        existingOrder.setQuantity(updateRequest.getQuantity());

        return orderRepository.save(existingOrder);
    }
    
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}

