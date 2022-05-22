package com.trustmenet.controllers;

import com.trustmenet.repositories.dto.UserDto;
import com.trustmenet.services.RegistrationService;
import com.trustmenet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/registration")
    public UserDto registration(@RequestBody UserDto user) {
        registrationService.registerUser(user);
        return user;
    }

    @GetMapping("/registration/{token}")
    public boolean checkRegistrationTokenExistence(@PathVariable String token) {
        return registrationService.openRegistrationToken(token);
    }

    @PostMapping("/login")
    public Object login(@RequestBody UserDto user) {
        return new Object() {
            public String getToken() {
                return registrationService.login(user.getLogin(), user.getPassword());
            }
        };
    }

    @PostMapping("/pass-recovery")
    public boolean passwordRecovery(@RequestBody String email) {
        return registrationService.passwordRecovery(email);
    }

    @GetMapping("/pass-recovery/{token}")
    public boolean checkPasswordRecoveryTokenExistence(@PathVariable String token) {
        return registrationService.openPasswordRecoveryToken(token);
    }

    @PutMapping("/pass-recovery/{token}")
    public boolean editPassword(@PathVariable String token, @RequestBody String password) {
        return registrationService.editPassword(token, password);
    }
}
