package com.hservice.services;

import com.hservice.exceptions.NotFoundException;
import com.hservice.models.User;

public interface UserService extends CrudService<User,Long> {

    boolean existsByEmailAndPassword(String email,String password);

    boolean existsByUserNameAndPassword(String userName,String password);

    User findByUserNameAndPassword(String userName, String password) throws NotFoundException;
}