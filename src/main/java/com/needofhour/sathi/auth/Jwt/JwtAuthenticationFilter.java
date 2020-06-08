package com.needofhour.sathi.auth.Jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.needofhour.sathi.user.service.UserDetailsServiceImpl;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
//}

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		
		String userName=null;
		String jwt=null;
		
		final String authorizationHeader= req.getHeader("Authorization");
		//System.out.println("token"+authorizationHeader);
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
			jwt=authorizationHeader.substring(7);
			//System.out.println("jwt: "+jwt);
			userName=jwtTokenUtil.extractUsername(jwt);
			//System.out.println("userName: "+userName);
			
		}
		//System.out.println("authentication: "+SecurityContextHolder.getContext().getAuthentication());
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(userName);
			System.out.println("Matched username: "+userDetails.getUsername()+" Authorities:"+userDetails.getAuthorities());
			if(jwtTokenUtil.validateToken(jwt, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
						(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
			
		}
		chain.doFilter(req, res);
	}

}
