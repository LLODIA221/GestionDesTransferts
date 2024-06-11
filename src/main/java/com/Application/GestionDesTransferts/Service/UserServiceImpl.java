package com.Application.GestionDesTransferts.Service;


import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Models.User;
import com.Application.GestionDesTransferts.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getRole(), userDto.getFullname());
        return userRepository.save(user);
    }



    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.empty();
    }


}
