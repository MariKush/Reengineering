package com.trustmenet.repositories.dao;


import com.trustmenet.repositories.entities.UserDto;
import com.trustmenet.repositories.entities.enums.Role;
import com.trustmenet.repositories.entities.enums.UserAccountStatus;

import java.util.List;

public interface UserDao extends GenericDao<UserDto> {

    String TABLE_NAME = "users";

    UserDto getUserByLoginAndPassword(String login, String password);

    UserDto getUserByMail(String mail);

    UserDto getUserByLogin(String login);

    boolean addUserFriend(int userId, int friendId);

    void deleteUserFriend(int userId, int friendId);

    List<UserDto> getUserFriends(int userId);

    List<UserDto> searchUsersByLogin(String login);

    List<UserDto> searchUsersByLogin(String login, Role role);

    List<UserDto> getUsersPage(int limit, int offset);

    boolean checkUsersFriendship(int firstUserId, int secondUserId);

    void updateUserPhoto(int idImage, int userId);

    void updateUserStatus(int id, UserAccountStatus status);

    void increaseUserRating(int userId, int scorePlus);

    void changePassword(String password, String login);

    boolean appointToModer(int userId);

    boolean cancelAppointingToModer(int userId);

}
