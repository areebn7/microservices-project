package com.webage.microservices_project.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webage.microservices_project.Model.Customer;
import com.webage.microservices_project.Repository.CustomersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomersRepository repo;

    @GetMapping
    public String homePage() {
        return "Welcome to my REST API!";
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
    public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer) {
        // Validate input:
        if (newCustomer.getName() == null
                || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }
        newCustomer = repo.save(newCustomer);

        // Location header will resemble
        // "http://localhost:8080/customers/27"
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> putCustomer(
            @RequestBody Customer newCustomer,
            @PathVariable("id") int id) {
        if (newCustomer.getId() != id || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }
        newCustomer = repo.save(newCustomer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") int id) {
        // Check if the customer exists
        Optional<Customer> customerOptional = repo.findById(id);
        if (customerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Delete the customer
        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
