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

import java.net.URI;
import java.util.Optional;

/**
 * Controller for managing customer records in the ADP Customer Management Application.
 * Provides endpoints for health check, CRUD operations, and partial updates on customers.
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomersRepository repo;

    /**
     * Endpoint to check the health of the customer management service.
     * 
     * @return A string indicating that the service is running.
     */
    @GetMapping
    public String health() {
        return "Service is running. Welcome to the ADP Customer Management Application!";
    }

    /**
     * Retrieves all customer records.
     * 
     * @return An iterable collection of all customers.
     */
    @GetMapping("/customers")
    public Iterable<Customer> getAll() {
        return repo.findAll();
    }

    /**
     * Retrieves a customer record by its ID.
     * 
     * @param id The ID of the customer to retrieve.
     * @return An optional containing the customer if found, otherwise empty.
     */
    @GetMapping("/customers/{id}")
    public Optional<Customer> getCustomer(@PathVariable("id") int id) {
        return repo.findById(id);
    }

    /**
     * Adds a new customer record.
     * 
     * @param newCustomer The customer to add.
     * @return A response entity with the creation status and location URI of the new customer.
     */
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

    /**
     * Updates an existing customer record by replacing it with the new customer data.
     * 
     * @param newCustomer The new customer data.
     * @param id The ID of the customer to update.
     * @return A response entity with the update status.
     */
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

    /**
     * Partially updates an existing customer record with the provided data.
     * 
     * @param updatedCustomer The partial customer data to update.
     * @param id The ID of the customer to update.
     * @return A response entity with the update status.
     */
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

    /**
     * Deletes a customer record by its ID.
     * 
     * @param id The ID of the customer to delete.
     * @return A response entity with the deletion status.
     */
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
