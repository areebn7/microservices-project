package com.webage.microservices_project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.webage.microservices_project.Model.Customer;



@SpringBootTest
class CustomerTest {

	@Test
	void contextLoads() {
	}

    @Test
    public void testConstructor() {
        // Create a customer object using the parameterized constructor
        Customer customer = new Customer(1, "Areeb Nabi", "areeb123", "areeb.nabi@gmail.com");

        // Verify that the fields are initialized correctly
        assertEquals(1, customer.getId());
        assertEquals("Areeb Nabi", customer.getName());
        assertEquals("areeb123", customer.getPassword());
        assertEquals("areeb.nabi@gmail.com", customer.getEmail());
    }

    @Test
    public void testSetAndGetId() {
        Customer customer = new Customer();
        
        int id = 1;
        customer.setId(id);

        assertEquals(id, customer.getId());
    }

    @Test 
    public void testSetAndGetName() {
        Customer customer = new Customer();

        String name = "Areeb";
        customer.setName(name);

        assertEquals(name, customer.getName());
    }

    @Test
    public void testSetAndGetPassword() {
        Customer customer = new Customer();

        String password = "password321";
        customer.setPassword(password);

        assertEquals(password, customer.getPassword());
    }

    @Test
    public void testSetAndGetEmail() {
        Customer customer = new Customer();

        String email = "areeb.nabi@blah.com";
        customer.setEmail(email);

        assertEquals(email, customer.getEmail());
    }
}
