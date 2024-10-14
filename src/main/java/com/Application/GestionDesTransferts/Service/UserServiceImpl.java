package com.Application.GestionDesTransferts.Service;


import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Models.User;
import com.Application.GestionDesTransferts.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    //ajouter un noul utilisateur
    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getRole(), userDto.getFullname());
        return userRepository.save(user);
    }

    //utilisateur par son email
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    //utilisateur par son email
    @Override
    public User findbyEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Implémentation de la méthode de mise à jour
    @Override
    public User updateUser(UserDto userDto, Long id) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setEmail(userDto.getEmail());
            existingUser.setFullname(userDto.getFullname());
            existingUser.setRole(userDto.getRole());
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            return userRepository.save(existingUser);
        }
        throw new RuntimeException("Utilisateur non trouvé");
    }

    // Implémentation de la méthode de suppression
    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }
// afficher la liste de tous les utilisateurs existantes
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}