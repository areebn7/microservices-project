package com.webage.microservices_project.Repository;

import org.springframework.data.repository.CrudRepository;

import com.webage.microservices_project.Model.Customer;

/**
 * Repository interface for performing CRUD operations on {@link Customer} entities.
 * 
 * This interface extends {@link CrudRepository}, providing basic CRUD functionality
 * and allowing for database interactions with {@link Customer} objects.
 */
public interface CustomersRepository extends CrudRepository<Customer, Integer> {
    
}
