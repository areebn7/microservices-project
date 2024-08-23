package com.webage.microservices_project.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a customer in the system.
 * This class maps to the "CUSTOMERS" table in the database.
 */
@Entity
@Table(name="CUSTOMERS")
public class Customer {

    // Define customer variables
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

    @Column(name="CUSTOMER_NAME")
    private String name;
    private String password;
    private String email;

    /**
     * No-argument constructor for the Customer class.
     * Required by JPA for entity instantiation.
     */
    public Customer() {
    }

    /**
     * Parameterized constructor for the Customer class.
     * 
     * @param id The unique identifier of the customer.
     * @param name The name of the customer.
     * @param password The password of the customer.
     * @param email The email of the customer.
     */
    public Customer(int id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    /**
     * Gets the unique identifier of the customer.
     * 
     * @return The customer's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the customer.
     * 
     * @param id The customer's ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the customer.
     * 
     * @return The customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * 
     * @param name The customer's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the password of the customer.
     * 
     * @return The customer's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the customer.
     * 
     * @param password The customer's password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email of the customer.
     * 
     * @return The customer's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the customer.
     * 
     * @param email The customer's email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
