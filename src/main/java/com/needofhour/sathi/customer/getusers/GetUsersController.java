package com.needofhour.sathi.customer.getusers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.needofhour.sathi.customer.data.model.Customer;
import com.needofhour.sathi.customer.getusers.data.model.CustomerLocation;
import com.needofhour.sathi.customer.getusers.data.model.Location;
import com.needofhour.sathi.customer.repo.CustomerRepo;

@RestController
@RequestMapping("/api")
public class GetUsersController {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@GetMapping("/nearBy")
	public List<CustomerLocation> getUsersByLocation(@RequestBody Location location)
	{
		List<Customer> customerList=customerRepo.findAll();
		List<CustomerLocation> customerLocationList=new ArrayList<CustomerLocation>();
		for(Customer customer: customerList)
		{
			customerLocationList.add(new CustomerLocation(
					customer.getFirstName()+" "+customer.getLastName(),customer.getPhone(),"2 km"));
		}
		return customerLocationList;
	}
}
