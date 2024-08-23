package com.webage.microservices_project.API.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.webage.microservices_project.API.Model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @GetMapping
    public String homePage() {
        return "Welcome to my REST API!";
    }

    private List<Customer> customers = new ArrayList<>();

    public CustomerController() {
        customers.add(new Customer(1, "Areeb Nabi", "password123", "areeb.nabi@gmail.com"));
        customers.add(new Customer(2, "John Doe", "johndoe456", "john.doe@example.com"));
        customers.add(new Customer(3, "Jane Smith", "janesmith789", "jane.smith@example.com"));
    }

    // Get all customers
    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customers;
    }
        // Get a customer by ID
    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable("id") int id) {
        Optional<Customer> customer = customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        return customer.orElse(null);
    }
}
