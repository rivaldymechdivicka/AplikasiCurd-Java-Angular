package com.example.Backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Backend.dto.CreateCustomerRequest;
import com.example.Backend.entity.Customer;
import com.example.Backend.service.CustomerService;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer createCustomer(@ModelAttribute CreateCustomerRequest customerDTO) {
        Customer customer = customerService.createCustomer(customerDTO);
        return customer;
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long customerId,
            @ModelAttribute CreateCustomerRequest customerDTO) {
        if (!customerService.getCustomerById(customerId).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        if (!customerService.getCustomerById(customerId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> serveImage(@PathVariable String imageName) {
        Resource file = new ClassPathResource("static/img/customer/" + imageName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}