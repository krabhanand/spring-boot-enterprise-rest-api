package com.needofhour.sathi.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.needofhour.sathi.user.User;
import com.needofhour.sathi.user.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(userName);

		if(user==null) throw new UsernameNotFoundException(userName + " not found.");
		
		UserDetails userDetails=new UserDetailsImpl(user);
		System.out.println(user.getUserName()+user.getPassword()+user.getRoles());
		return userDetails;
	}
}
