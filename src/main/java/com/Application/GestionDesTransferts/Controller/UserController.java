package com.Application.GestionDesTransferts.Controller;
import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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


}
