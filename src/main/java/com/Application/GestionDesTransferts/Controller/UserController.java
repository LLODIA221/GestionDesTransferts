package com.Application.GestionDesTransferts.Controller;
import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Models.User;
import com.Application.GestionDesTransferts.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfuly!");
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("user-page")
    public String userPage(Model model, Principal principal) {
        UserDto userDto = new UserDto();
        userDto.setEmail(principal.getName());
        //recuperation du role de l'utilisateurteur
        model.addAttribute("role", userDetailsService.loadUserByUsername(principal.getName()).getAuthorities());
        model.addAttribute("user", userDto);
        return "redirect:/dashboard";

    }


    @GetMapping("admin-page")
    public String adminPage(Model model, Principal principal) {
      UserDto userDto = new UserDto();
      userDto.setEmail(principal.getName());
      //recuperation du role de l'utilisateurteur
        model.addAttribute("role", userDetailsService.loadUserByUsername(principal.getName()).getAuthorities());
      model.addAttribute("user", userDto);
        model.addAttribute("fullname", userDto.getFullname());


        return "redirect:/dashboard";

    }

    // Afficher la page pour modifier l'utilisateur
    @GetMapping("/update-user/{id}")
    public String getUpdateUserPage(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            UserDto userDto = new UserDto();
            userDto.setEmail(user.get().getEmail());
            userDto.setFullname(user.get().getFullname());
            userDto.setRole(user.get().getRole());
            model.addAttribute("user", userDto);
            return "update-user";
        }
        model.addAttribute("error", "Utilisateur non trouvé");
        return "error";
    }

    // Sauvegarder la mise à jour de l'utilisateur
    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UserDto userDto, Model model) {
        userService.updateUser(userDto, id);
        model.addAttribute("message", "Mise à jour réussie");
        return "redirect:/dashboard";
    }

    // Supprimer un utilisateur
    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("message", "Utilisateur supprimé avec succès");
        return "redirect:/dashboard";
    }

    //pages de l'admin
    @GetMapping("/admin-settings")
    public String getAdminSettings(Model model) {
        // Récupérer la liste des utilisateurs
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin-settings";
    }


}
