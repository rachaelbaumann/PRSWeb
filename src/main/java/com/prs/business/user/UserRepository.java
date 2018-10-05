package com.prs.business.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	// layout of method name is super important! It HAS to be that way!!!

	User findByUserNameAndPassword(String userName, String password);
	//SELECT * FROM user -- find
	// WHERE clause -- by
	// get by uname and password -- UserNameAndPassword

}
