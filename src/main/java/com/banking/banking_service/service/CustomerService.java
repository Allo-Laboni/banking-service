package com.banking.banking_service.service;

import com.banking.banking_service.domain.Customer;
import com.banking.banking_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return repository.findById(id);
    }

    public String addCustomer(Customer customer) throws Exception {
        Optional<Customer> existingCustomer = repository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            return "Customer already exists with the same email";
        }
        repository.save(customer);
        return "Customer added successfully";
    }

    public String updateCustomer(Long id, Customer updatedCustomer) {
        // Fetch the existing customer from the database
        Optional<Customer> existingCustomerOpt = repository.findById(id);
        if (!existingCustomerOpt.isPresent()) {
            return "Customer not found";
        }

        Customer existingCustomer = existingCustomerOpt.get();

        // Ensure that the email cannot be updated
        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().equals(existingCustomer.getEmail())) {
            return "Email cannot be updated";
        }

        // Update the customer fields (except email)
        if (updatedCustomer.getFirstName() != null) {
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
        }
        if (updatedCustomer.getLastName() != null) {
            existingCustomer.setLastName(updatedCustomer.getLastName());
        }
        if (updatedCustomer.getPhone() != null) {
            existingCustomer.setPhone(updatedCustomer.getPhone());
        }

        // Save the updated customer
        repository.save(existingCustomer);
        return "Customer details updated successfully";
    }


    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}
