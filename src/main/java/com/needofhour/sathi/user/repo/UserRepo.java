package com.needofhour.sathi.user.repo;

import org.springframework.data.repository.CrudRepository;

import com.needofhour.sathi.user.User;

public interface UserRepo extends CrudRepository<User, String> {
	public User findByUserName(String userName);

}
