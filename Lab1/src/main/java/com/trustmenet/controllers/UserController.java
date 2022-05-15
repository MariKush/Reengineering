package com.trustmenet.controllers;


import com.trustmenet.repositories.entities.UserDto;
import com.trustmenet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users/{id}")
    public UserDto getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PutMapping("users")
    public void updateUser(@RequestBody @Valid UserDto editedUser) {
        userService.updateUserProfile(editedUser);
    }

    @PutMapping("/users/password/{login}")
    public void changePassword(@PathVariable String login,
                               @RequestBody String newPassword) {
        userService.changeUserPassword(login, newPassword);
    }

    @GetMapping("/users/password/check")
    public boolean checkPasswords(@RequestParam(value = "login") String login,
                                  @RequestParam(value = "password") String password) {
        return userService.checkPasswords(login, password);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(value = "usersCount") int usersCount) {
        return userService.getNextPageOfUsers(usersCount);
    }

    @PostMapping("/users/{id}/addFriend")
    public boolean addFriend(@PathVariable int id,
                             @RequestBody int friendId) {
        return userService.addUserFriend(id, friendId);
    }

    @PostMapping("/users/{id}/removeFriend")
    public boolean removeFriend(@PathVariable int id,
                                @RequestBody int friendId) {
        return userService.deleteUserFriend(id, friendId);
    }

    @GetMapping("/users/{id}/checkFriend/{friendId}")
    public boolean checkFriend(@PathVariable int id, @PathVariable int friendId) {
        return userService.checkUsersFriendship(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<UserDto> getFriends(@PathVariable int id) {
        List<UserDto> u = userService.getUserFriends(id);
        System.out.println("sss");
        return u;
    }

    @PostMapping("/users/appointToModer")
    public boolean appointToModer(@RequestBody int userId) {
        return userService.appointToModer(userId);
    }

    @PostMapping("/users/cancelAppointingToModer")
    public boolean cancelAppointingToModer(@RequestBody int userId) {
        return userService.cancelAppointingToModer(userId);
    }
}
