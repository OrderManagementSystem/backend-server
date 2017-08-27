package com.vk.oms.controller;

import com.vk.oms.controller.exceptions.BadRequestException;
import com.vk.oms.model.Customer;
import com.vk.oms.model.Performer;
import com.vk.oms.model.User;
import com.vk.oms.repository.UserRepository;
import com.vk.oms.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/authenticated")
    @PreAuthorize("isAuthenticated()")
    public User getLoggedUser() {
        return userRepository.findOne(SecurityUtils.getLoggedUser().getId());
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody UserRegistration registration) {
        if (userRepository.findByUsername(registration.username) != null) {
            throw new BadRequestException(String.format(
                    "User '%s' already exists", registration.username));
        }

        User user;
        switch (registration.userType) {
            case "Performer":
                user = new Performer(registration.username, registration.password, new BigDecimal(300));
                break;
            case "Customer":
                user = new Customer(registration.username, registration.password, new BigDecimal(500));
                break;
            default:
                throw new BadRequestException(String.format("Unknown user type: %s", registration.userType));
        }

        return userRepository.save(user);
    }

    public static class UserRegistration {

        public String username;
        public String password;
        public String userType;
    }
}
