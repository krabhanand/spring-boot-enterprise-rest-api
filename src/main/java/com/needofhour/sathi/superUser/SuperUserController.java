package com.needofhour.sathi.superUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.needofhour.sathi.auth.LoginRequest;
import com.needofhour.sathi.head.data.model.Head;
import com.needofhour.sathi.head.repo.Header;
import com.needofhour.sathi.user.User;
import com.needofhour.sathi.user.repo.UserRepo;

@RestController
@PreAuthorize("hasRole('SUPER')")
public class SuperUserController {
	
	@Autowired
	private Header superUsersUseRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/heads")
	public @ResponseBody Head addHead(@RequestBody Head head){
		System.out.println(head.getPassword()+head.getUnitName()+head.getUserName());
		superUsersUseRepo.save(head);
		User user=new User();
		user.setUserName(head.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(head.getPassword()));
		user.setRoles("ROLE_HEAD");
		user.setActive(true);
		userRepo.save(user);
		return  head;
	}
	
	@GetMapping("/heads/{userName}")
	public @ResponseBody Head getHeadByUserName(@PathVariable String userName){

		return  superUsersUseRepo.findByUserName(userName);
	}
	
	@PreAuthorize("hasRole('SUPER')")
	@GetMapping("/heads")
	public @ResponseBody List<Head> getHeads(){
		Iterable<Head> it=superUsersUseRepo.findAll();
		List<Head> heads=new ArrayList<Head>();
		for(Head head: it){
			heads.add(head);
		}
		return  heads;
	}	
	
	@PostMapping("/heads/{userName}")
	public @ResponseBody Head updateHead(@PathVariable String userName, @RequestBody Head head){
		superUsersUseRepo.delete(superUsersUseRepo.findByUserName(userName));
		userRepo.delete(userRepo.findByUserName(userName));
		User user=new User();
		user.setUserName(head.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(head.getPassword()));
		user.setRoles("ROLE_HEAD");
		user.setActive(true);
		userRepo.save(user);
		superUsersUseRepo.save(head);
		return head;
	}
	

	
	@DeleteMapping("/heads/{userName}")
	public void deleteHead(@PathVariable String userName){
		superUsersUseRepo.delete(superUsersUseRepo.findByUserName(userName));
		userRepo.delete(userRepo.findByUserName(userName));
		return;
	}
	
	@PostMapping("/super")
	public @ResponseBody LoginRequest updateSuper( @RequestBody LoginRequest head){
		User user=new User();
		user.setUserName(head.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(head.getPassword()));
		user.setRoles("ROLE_SUPER");
		user.setActive(true);
		userRepo.save(user);
		//superUsersUseRepo.save(head);
		return head;
	}

}
