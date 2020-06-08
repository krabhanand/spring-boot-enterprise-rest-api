package com.needofhour.sathi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.needofhour.sathi.auth.Jwt.JwtTokenUtil;
import com.needofhour.sathi.user.repo.UserRepo;
import com.needofhour.sathi.user.service.UserDetailsServiceImpl;

@RestController
public class AuthenticateController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
    //private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticateController(UserRepo userRepo,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        //this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> authentictE(@RequestBody LoginRequest loginRequest) throws Exception {
    	try{
    		System.out.println("before authentication");
    	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
    	System.out.println("after authentication");
    	}
    	catch(BadCredentialsException e){
    		throw new Exception("Incorrect credentials provided", e);
    	}
    	//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //userRepo.save(user);
        final UserDetails userDetails=userDetailsService.loadUserByUsername(loginRequest.getUserName());
        final String jwt=jwtTokenUtil.generateToken(userDetails);
        System.out.println(jwt);
        return ResponseEntity.ok(jwt);
    }
}
