package com.needofhour.sathi.head;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.needofhour.sathi.customer.data.model.Customer;
import com.needofhour.sathi.customer.repo.CustomerRepo;
import com.needofhour.sathi.head.exception.CustomerAlreadyExistsException;
import com.needofhour.sathi.head.exception.CustomerExistsErrorResponse;

@RestController
public class HeadController {

	
	@Autowired
	private CustomerRepo customerRepo;
	
	@PostMapping("/customers")
	public void addCustomers(@RequestBody Customer customer){
		Customer testCustomer=customerRepo.findByregId(customer.getRegId());
		if(testCustomer!=null) throw new CustomerAlreadyExistsException("Customer already exists");
		customerRepo.save(customer);
	}
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		Iterable<Customer> it=customerRepo.findAll();
		List<Customer> customerList=new ArrayList<Customer>();
		for(Customer customer: it){
			customerList.add(customer);
		}
		return customerList;
	}
	@ExceptionHandler
	public ResponseEntity<CustomerExistsErrorResponse> errorCustomerNotFoundException(CustomerAlreadyExistsException exc){
		CustomerExistsErrorResponse stdErrRes=new CustomerExistsErrorResponse();
		stdErrRes.setMessage(exc.getMessage());
		stdErrRes.setStatus(HttpStatus.CONFLICT.value());
		stdErrRes.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(stdErrRes,HttpStatus.CONFLICT);
		
	}
}
