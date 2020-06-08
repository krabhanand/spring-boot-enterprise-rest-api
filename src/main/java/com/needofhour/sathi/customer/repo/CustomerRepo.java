package com.needofhour.sathi.customer.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.needofhour.sathi.customer.data.model.Customer;

public interface CustomerRepo extends CrudRepository<Customer, String> {
	public Customer findByregId(String regId);
	public List<Customer> findAll();
	

}
