package com.webage.microservices_project.API.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webage.microservices_project.API.Model.Customer;
import com.webage.microservices_project.API.repository.CustomersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired CustomersRepository repo;

    @GetMapping
    public String homePage() {
        return "Welcome to my REST API!";
    }


    @GetMapping("/customers")
    public Iterable<Customer> getAll() {
        return repo.findAll();
    }

    @GetMapping("/customers/{id}") 
    public Optional<Customer> getCustomer(@PathVariable("id")int id) {
        return repo.findById(id);
        
    }

    
    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer) {
        // Validate input:
        if ( newCustomer.getName()==null
            || newCustomer.getEmail() == null) { 
            return ResponseEntity.badRequest().build();
        }
        newCustomer=repo.save(newCustomer);

        // Location header will resemble 
        // "http://localhost:8080/customers/27"
        URI location =
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newCustomer.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

     @PutMapping("/{id}")
    public ResponseEntity<?> putCustomer(
        @RequestBody Customer customer, 
        @PathVariable long id) {

        if (customer.getId()!=id
            || customer.getName()==null
            || customer.getEmail() == null) {
                return ResponseEntity.badRequest().build();
        }

        repo.save(customer);
        return ResponseEntity.ok().build();
    }
}
