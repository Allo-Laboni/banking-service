package com.banking.banking_service.controller;

import com.banking.banking_service.domain.Customer;
import com.banking.banking_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public String addCustomer(@RequestBody Customer customer) throws Exception {
        return service.addCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        String message = service.updateCustomer(id, updatedCustomer);
        if (message.equals("Customer details updated successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(400).body(message);  // Bad request status for errors like "Email cannot be updated"
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}

