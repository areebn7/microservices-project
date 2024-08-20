package com.webage.microservices_project.API.repository;

import com.webage.microservices_project.API.Model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository <Customer, Integer> {
    
}
