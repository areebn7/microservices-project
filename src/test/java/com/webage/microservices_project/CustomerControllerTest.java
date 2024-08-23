package com.webage.microservices_project;

import com.webage.microservices_project.Controller.CustomerController;
import com.webage.microservices_project.Model.Customer;
import com.webage.microservices_project.Repository.CustomersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomersRepository repo;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1, "Areeb Nabi", "password123", "areeb.nabi@gmail.com");
    }

    @Test
    void testHealthCheck() {
        String response = customerController.health();
        assertEquals("Service is running. Welcome to the ADP Customer Management Application!", response);
    }

    @Test
    void testGetAllCustomers() {
        when(repo.findAll()).thenReturn(List.of(customer));
        Iterable<Customer> customers = customerController.getAll();
        assertEquals(1, ((List<Customer>) customers).size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_Found() {
        when(repo.findById(anyInt())).thenReturn(Optional.of(customer));
        Optional<Customer> foundCustomer = customerController.getCustomer(1);
        assertEquals("Areeb Nabi", foundCustomer.get().getName());
        verify(repo, times(1)).findById(anyInt());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(repo.findById(anyInt())).thenReturn(Optional.empty());
        Optional<Customer> foundCustomer = customerController.getCustomer(1);
        assertEquals(Optional.empty(), foundCustomer);
        verify(repo, times(1)).findById(anyInt());
    }

    @Disabled
    @Test
    void testAddCustomer_Success() {
        when(repo.save(any(Customer.class))).thenReturn(customer);

        Customer newCustomer = new Customer(2, "John Doe", "johndoe456", "john.doe@example.com");
        ResponseEntity<String> response = customerController.addCustomer(newCustomer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(repo, times(1)).save(any(Customer.class));
    }

    @Test
    void testAddCustomer_BadRequest() {
        Customer newCustomer = new Customer(2, null, null, null);
        ResponseEntity<String> response = customerController.addCustomer(newCustomer);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name and Email are required fields.", response.getBody());
        verify(repo, times(0)).save(any(Customer.class));
    }

    @Test
    void testPutCustomer_Success() {
        when(repo.existsById(anyInt())).thenReturn(true);
        when(repo.save(any(Customer.class))).thenReturn(customer);

        ResponseEntity<String> response = customerController.putCustomer(customer, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repo, times(1)).existsById(anyInt());
        verify(repo, times(1)).save(any(Customer.class));
    }

    @Test
    void testPutCustomer_NotFound() {
        when(repo.existsById(anyInt())).thenReturn(false);

        ResponseEntity<String> response = customerController.putCustomer(customer, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer with ID 1 not found.", response.getBody());
        verify(repo, times(1)).existsById(anyInt());
        verify(repo, times(0)).save(any(Customer.class));
    }

    @Test
    void testPatchCustomer_Success() {
        when(repo.findById(anyInt())).thenReturn(Optional.of(customer));
        when(repo.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Name");

        ResponseEntity<String> response = customerController.patchCustomer(updatedCustomer, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repo, times(1)).findById(anyInt());
        verify(repo, times(1)).save(any(Customer.class));
    }

    @Test
    void testPatchCustomer_NotFound() {
        when(repo.findById(anyInt())).thenReturn(Optional.empty());

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Name");

        ResponseEntity<String> response = customerController.patchCustomer(updatedCustomer, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repo, times(1)).findById(anyInt());
        verify(repo, times(0)).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer_Success() {
        when(repo.findById(anyInt())).thenReturn(Optional.of(customer));

        ResponseEntity<?> response = customerController.deleteCustomer(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repo, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteCustomer_NotFound() {
        when(repo.findById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<?> response = customerController.deleteCustomer(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repo, times(1)).findById(anyInt());
        verify(repo, times(0)).deleteById(anyInt());
    }
}
