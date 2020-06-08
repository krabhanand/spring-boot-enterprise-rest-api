package com.needofhour.sathi.head.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.needofhour.sathi.head.data.model.Head;

public interface Header extends CrudRepository<Head, String> {
	
public List<Head> findAllByUserName(String userName);
public Head findByUserName(String userName);
}
