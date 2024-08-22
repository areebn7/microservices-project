package com.webage.microservices_project;

import com.webage.microservices_project.Controller.CustomerController;
import com.webage.microservices_project.Model.Customer;
import com.webage.microservices_project.Repository.CustomersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomersRepository repo;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHomePage() {
        String result = customerController.homePage();
        assertEquals("Welcome to my REST API!", result);
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        Customer customer1 = new Customer(1, "Cristiano Ronaldo", "cristiano@example.com", "password123");
        Customer customer2 = new Customer(2, "Jane Doe", "jane@example.com", "password456");
        when(repo.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // Act
        Iterable<Customer> result = customerController.getAll();

        // Assert
        assertEquals(Arrays.asList(customer1, customer2), result);
    }

    @Test
    public void testGetCustomerById() {
        // Arrange
        Customer customer = new Customer(1, "Cristiano Ronaldo", "cristiano@example.com", "password123");
        when(repo.findById(1)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> result = customerController.getCustomer(1);

        // Assert
        assertEquals(Optional.of(customer), result);
    }

    @Test
    public void testAddCustomer_Success() {
        // Arrange
        Customer customer = new Customer(1, "Cristiano Ronaldo", "cristiano@example.com", "password123");
        when(repo.save(any(Customer.class))).thenReturn(customer);

        // Act
        ResponseEntity<?> response = customerController.addCustomer(customer);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(URI.create("/api/customers/1"), response.getHeaders().getLocation());
    }

    @Test
    public void testAddCustomer_Invalid() {
        // Arrange
        Customer customer = new Customer(0, null, "cristiano@example.com", "password123");

        // Act
        ResponseEntity<?> response = customerController.addCustomer(customer);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testPutCustomer_Success() {
        // Arrange
        Customer customer = new Customer(1, "Cristiano Ronaldo", "cristiano@example.com", "password123");
        when(repo.save(any(Customer.class))).thenReturn(customer);

        // Act
        ResponseEntity<?> response = customerController.putCustomer(customer, 1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testPutCustomer_Invalid() {
        // Arrange
        Customer customer = new Customer(2, "Cristiano Ronaldo", "cristiano@example.com", "password123");

        // Act
        ResponseEntity<?> response = customerController.putCustomer(customer, 1);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomer_Success() {
        // Arrange
        Customer customer = new Customer(1, "Cristiano Ronaldo", "cristiano@example.com", "password123");
        when(repo.findById(1)).thenReturn(Optional.of(customer));

        // Act
        ResponseEntity<?> response = customerController.deleteCustomer(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteCustomer_NotFound() {
        // Arrange
        when(repo.findById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = customerController.deleteCustomer(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
