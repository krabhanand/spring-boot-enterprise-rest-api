package com.needofhour.sathi.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.needofhour.sathi.OTP.OTP;
import com.needofhour.sathi.customer.data.model.Customer;
import com.needofhour.sathi.customer.exception.RegIdNotFoundException;
import com.needofhour.sathi.customer.repo.CustomerRepo;
import com.needofhour.sathi.customer.response.SendOTPResponse;
import com.needofhour.sathi.customer.response.VerifyRegIdResponse;
import com.needofhour.sathi.user.User;
import com.needofhour.sathi.user.repo.UserRepo;

@RestController
public class AddUserController {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/sendOTP")
	public @ResponseBody ResponseEntity<SendOTPResponse> addUserSendOTP(@RequestParam String phone,@RequestParam String regId){

		//see if the number is same as previously provided or not
		//create user with username regId
		//check if header with regId is present
		User user=new User();
		user.setUserName(regId);
		
		//generate 8 digit OTP
		OTP oTP=new OTP();
		String otp=oTP.getOTP();
		System.out.println(otp);
		
		//set password
		user.setPassword(bCryptPasswordEncoder.encode(otp));
		
		//set roles
		user.setRoles("ROLE_CUSTOMER");
		
		//save user
		userRepo.save(user);
		
		//save mobile number
		Customer customer=customerRepo.findByregId(regId);
		customer.setPhone(phone);
		customerRepo.delete(customer);
		customerRepo.save(customer);
		
		//build response
		SendOTPResponse sendOTPResponse=new SendOTPResponse();
		sendOTPResponse.setValueType("otp");
		sendOTPResponse.setValue("otp has been sent");
		
		
		//build response header
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("regId", regId);
		
		//send otp
		
		
	    //send response
		return ResponseEntity.ok()
			      .headers(responseHeaders)
			      .body(sendOTPResponse);
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/verifyPehchan", method = RequestMethod.POST)
	public ResponseEntity<VerifyRegIdResponse> verifyRegId(@RequestParam String regId){
		Customer customer=customerRepo.findByregId(regId);
		
		//throw exceptioned of prohibited if no match found, records with this regId is not present, click here to lodge a complain
		if(customer==null) throw new RegIdNotFoundException("The id provided does not have a user registered with us");
		
		//add header of regId
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("regId", regId);
		
		//if match found but account already created, directly send OTP(after storing current otp as password in database
		if(userRepo.findByUserName(regId)!=null) {
			
			//generate 8 digit OTP
			OTP oTP=new OTP();
			String otp=oTP.getOTP();
			
			//create user object
			User user=new User();
			//add userName, password, roles to the object
			user.setPassword(bCryptPasswordEncoder.encode(otp));
			user.setUserName(regId);
			user.setRoles("ROLE_CUSTOMER");
			
			//delete user by regId username
			userRepo.delete(userRepo.findByUserName(regId));
			
			//save user by user object
			userRepo.save(user);
			
			//send otp  to mobile number
			//set response type to otp being sent
			VerifyRegIdResponse verifyRegIdResponse=new VerifyRegIdResponse();
			verifyRegIdResponse.setValueType("otp");
			verifyRegIdResponse.setValue("OTP is being sent");
			
			//send otp
			System.out.println("OTP is being sent");
			
			//send response
			return ResponseEntity.ok()
				      .headers(responseHeaders)
				      .body(verifyRegIdResponse);
				
			
			
		}
		VerifyRegIdResponse verifyRegIdResponse=new VerifyRegIdResponse();
		verifyRegIdResponse.setValueType("name");
		verifyRegIdResponse.setValue(customerRepo.findByregId(regId).getFirstName()+customerRepo.findByregId(regId).getLastName());
		
		// send a hashed value with one key on the server
		return ResponseEntity.ok()
			      .headers(responseHeaders)
			      .body(verifyRegIdResponse);
	}

	//create a responsebody with 2 feilds
	//valueType
	//value
	//valueType has "name" or "otp"
	//value has the name or the otp

}


//create an OTP class to generate OTP