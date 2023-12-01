package com.company.managementsystem.controller;

import com.company.managementsystem.controller.dto.UserDto;
import com.company.managementsystem.controller.dto.converter.DtoConverter;
import com.company.managementsystem.entity.User;
import com.company.managementsystem.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService userService;
    private final DtoConverter<User, UserDto> userDtoConverter;

    @Autowired
    public RegistrationController(UserService userService, DtoConverter<User, UserDto> userDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/registrationForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration-form";
    }

    @PostMapping("/register")
    public String register (@Valid UserDto userDto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {


        if (bindingResult.hasErrors()) {
            return "registration-form";
        }

        String username = userDto.getUsername();
        Optional<User> existingUser = userService.findByUsername(username);
        if (existingUser.isPresent()) {
            model.addAttribute("userDto", new UserDto());
            model.addAttribute("registrationError", "User with this username already exists");
            return "registration-form";
        }

        User user = userDtoConverter.convertToEntity(userDto);
        userService.saveUser(user);
        LOGGER.info("User with username: " + username + " has been successfully registered");
        redirectAttributes.addFlashAttribute("registered",
                "You have been successfully registered. You can sign in into your account.");
        return "redirect:/login-page";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}


