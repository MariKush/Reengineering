package com.trustmenet.services;


import com.trustmenet.repositories.dao.UserDao;
import com.trustmenet.repositories.entities.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto getUserById(int id){
        return userDao.get(id);
    }

    public void updateUserProfile(UserDto editedUser) {
        UserDto currentUser = userDao.getUserByLogin(editedUser.getLogin());

        currentUser.setFirstName(editedUser.getFirstName());
        currentUser.setSecondName(editedUser.getSecondName());
        currentUser.setProfile(editedUser.getProfile());
        currentUser.setImageId(editedUser.getImageId());

        userDao.update(currentUser);
    }

    public void changeUserPassword(String login, String newPassword) {
        userDao.changePassword(passwordEncoder.encode(newPassword), login);
    }

    public boolean checkPasswords(String login, String password) {
        UserDto currentUser = userDao.getUserByLogin(login);
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

    public List<UserDto> getNextPageOfUsers(int usersCount) {
        return userDao.getUsersPage(10, usersCount);
    }

    public boolean addUserFriend(int userId, int friendId) {
        return userDao.addUserFriend(userId, friendId);
    }

    public boolean deleteUserFriend(int userId, int friendId) {
        userDao.deleteUserFriend(userId, friendId);
        return true;
    }

    public boolean checkUsersFriendship(int id, int friendId) {
        return userDao.checkUsersFriendship(id, friendId);
    }

    public void increaseUserRating(int userId, int ratingPlus){
        userDao.increaseUserRating(userId, ratingPlus);
    }

    public List<UserDto> getUserFriends(int userId) {
        return userDao.getUserFriends(userId);
    }

    public boolean appointToModer(int userId) {
        return userDao.appointToModer(userId);
    }

    public boolean cancelAppointingToModer(int userId) {
        return userDao.cancelAppointingToModer(userId);
    }
}
