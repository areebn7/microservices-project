package com.webage.microservices_project.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webage.microservices_project.Model.Customer;
import com.webage.microservices_project.Repository.CustomersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomersRepository repo;

    @GetMapping
    public String health() {
        return "Service is running. Welcome to the ADP Customer Management Application!";
    }

    @GetMapping("/customers")
    public Iterable<Customer> getAll() {
        return repo.findAll();
    }

    @GetMapping("/customers/{id}")
    public Optional<Customer> getCustomer(@PathVariable("id") int id) {
        return repo.findById(id);
    }

    @PostMapping("/customers")
    public ResponseEntity<String> addCustomer(@RequestBody Customer newCustomer) {
        // Validate input:
        if (newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().body("Name and Email are required fields.");
        }

        // Save the new customer
        newCustomer = repo.save(newCustomer);

        // Create the location URI
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();

        // Return a response with the creation confirmation
        return ResponseEntity.created(location)
                .body("Customer with ID " + newCustomer.getId() + " was created successfully.");
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<String> putCustomer(
            @RequestBody Customer newCustomer,
            @PathVariable("id") int id) {

        if (newCustomer.getId() != id || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().body("Invalid input: Customer ID mismatch or missing required fields.");
        }

        if (!repo.existsById(id)) {
            return ResponseEntity.status(404).body("Customer with ID " + id + " not found.");
        }

        repo.save(newCustomer);
        return ResponseEntity.ok("Customer with ID " + id + " was updated successfully.");
    }

    @PatchMapping("/customers/{id}")
    public ResponseEntity<String> patchCustomer(
            @RequestBody Customer updatedCustomer,
            @PathVariable("id") int id) {

        // Check if the customer exists
        Optional<Customer> existingCustomerOptional = repo.findById(id);
        if (existingCustomerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + id + " not found.");
        }
        // Get the existing customer from the optional
        Customer existingCustomer = existingCustomerOptional.get();

        // Update fields only if they are provided in the request body
        if (updatedCustomer.getName() != null) {
            existingCustomer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getEmail() != null) {
            existingCustomer.setEmail(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getPassword() != null) {
            existingCustomer.setPassword(updatedCustomer.getPassword());
        }
        // Save the updated customer
        repo.save(existingCustomer);

        return ResponseEntity.ok("Customer with ID " + id + " was partially updated successfully.");
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") int id) {
        // Check if the customer exists
        Optional<Customer> customerOptional = repo.findById(id);
        if (customerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + id + " does not exist.");
        }

        // Delete the customer
        repo.deleteById(id);

        return ResponseEntity.ok("Customer with ID " + id + " was successfully deleted.");
    }

}
