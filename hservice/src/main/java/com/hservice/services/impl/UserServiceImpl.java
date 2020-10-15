package com.hservice.services.impl;

import com.hservice.exceptions.AlreadyExistsException;
import com.hservice.exceptions.NotFoundException;
import com.hservice.models.User;
import com.hservice.repositories.RoleRepository;
import com.hservice.repositories.UserRepository;
import com.hservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public User save(User entity) throws AlreadyExistsException {
        if(userRepository.existsByUserNameAndPassword(entity.getUserName(), entity.getPassword())
                || userRepository.existsByEmailAndPassword(entity.getEmail(), entity.getPassword()))
            throw new AlreadyExistsException(String.format("User with user name: %s already exists",entity.getUserName()));
        return userRepository.save(entity);
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password) {
        return userRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public boolean existsByUserNameAndPassword(String userName, String password) {
        return existsByUserNameAndPassword(userName,password);
    }

    @Override
    public User findByUserNameAndPassword(String userName, String password) throws NotFoundException {
        return userRepository.findByUserNameAndPassword(userName,password).orElseThrow(NotFoundException::new);
    }
}