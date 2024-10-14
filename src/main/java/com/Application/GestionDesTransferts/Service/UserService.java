package com.Application.GestionDesTransferts.Service;



import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Models.User;

import java.util.Optional;
import java.util.List;


public interface UserService {

    // Autres méthodes déjà existantes
    List<User> findAllUsers();


    User save(UserDto userDto);

    Optional<User> getUserById(Long id);

    User findbyEmail(String email);

    // Méthode de mise à jour d'utilisateur
    User updateUser(UserDto userDto, Long id);

    // Méthode de suppression d'utilisateur
    void deleteUser(Long id);
}

