package com.vk.oms.controller;

import com.vk.oms.controller.exceptions.BadRequestException;
import com.vk.oms.model.Customer;
import com.vk.oms.model.Performer;
import com.vk.oms.model.User;
import com.vk.oms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody UserRegistration registration) {
        if (userRepository.findByUsername(registration.username) != null) {
            throw new BadRequestException(String.format(
                    "User '%s' already exists", registration.username));
        }

        if (!Objects.equals(registration.password, registration.passwordConfirmation)) {
            throw new BadRequestException("Passwords must match");
        }

        User user = registration.isCustomer
                ? new Customer(registration.username, registration.password)
                : new Performer(registration.username, registration.password);

        return userRepository.save(user);
    }

    private class UserRegistration {

        public String username;
        public String password;
        public String passwordConfirmation;
        public boolean isCustomer;
    }
}
