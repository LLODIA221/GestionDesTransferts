package com.Application.GestionDesTransferts.Config;

import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UserService userService;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            createDefaultUser("adminadmin@gmail.com", "admin221", "admin", "ADMIN");
            createDefaultUser("useruser@gmail.com", "user221", "user", "USER");
        };
    }

    private void createDefaultUser(String email, String password, String fullname, String role) {
        // Vérifier si l'utilisateur existe déjà
        if (userService.findbyEmail(email) == null) {
            // Créer l'utilisateur
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setFullname(fullname);
            userDto.setRole(role);

            // Sauvegarder l'utilisateur
            userService.save(userDto);
            System.out.println("Utilisateur par défaut créé avec succès: " + email);
        } else {
            System.out.println("L'utilisateur par défaut existe déjà: " + email);
        }
    }
}
