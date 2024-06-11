package com.Application.GestionDesTransferts.Service;



import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Models.User;

import java.util.Optional;

public interface UserService {
    User save (UserDto userDto);

    Optional<User> getUserById(Long id);
}
