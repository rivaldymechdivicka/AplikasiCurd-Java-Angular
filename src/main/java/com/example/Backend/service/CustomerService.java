package com.example.Backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Backend.dto.CreateCustomerRequest;
import com.example.Backend.entity.Customer;
import com.example.Backend.repository.CustomerRepository;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MinioService minioService;


    public List<Customer> getAllCustomers() {
        return customerRepository.findAllByIsActiveTrue();
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer createCustomer(CreateCustomerRequest customerDTO) {
        Customer customerModel = new Customer();
        customerModel.setCustomerName(customerDTO.getCustomerName());
        customerModel.setCustomerAddress(customerDTO.getCustomerAddress());
        customerModel.setCustomerPhone(customerDTO.getCustomerPhone());

        try {
            if (customerDTO.getPic() != null) {
                String picUrl = minioService.uploadFile(customerDTO.getPic());
                customerModel.setPic(picUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerModel.setIsActive(true);
        return customerRepository.save(customerModel);
    }

    public Customer updateCustomer(Long customerId, CreateCustomerRequest customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            mapDTOToEntity(existingCustomer, customerDTO);
            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
    }

    public void deleteCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        optionalCustomer.ifPresent(customer -> {
            customer.setIsActive(false);
            customerRepository.save(customer);
        });
    }

    private Customer mapDTOToEntity(Customer customer, CreateCustomerRequest customerDTO) {
        if (customer == null) {
            customer = new Customer();
        }
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setCustomerAddress(customerDTO.getCustomerAddress());
        customer.setCustomerPhone(customerDTO.getCustomerPhone());

        MultipartFile file = customerDTO.getPic();
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
            try {
                Path filePath = Paths.get("src/main/resources/static/img/customer/" + fileName);
                if (Files.notExists(filePath.getParent())) {
                    Files.createDirectories(filePath.getParent());
                }
                Files.write(filePath, file.getBytes());
                customer.setPic(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}