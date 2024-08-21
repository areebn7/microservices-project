package com.webage.microservices_project.repository;

import org.springframework.data.repository.CrudRepository;

import com.webage.microservices_project.Model.Customer;

public interface CustomersRepository extends CrudRepository <Customer, Integer> {
    
}
