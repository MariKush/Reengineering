package com.trustmenet.repositories.dao;


import com.trustmenet.repositories.entities.User;
import com.trustmenet.repositories.enums.Role;
import com.trustmenet.repositories.enums.UserAccountStatus;

import java.util.List;

public interface UserDao extends GenericDao<User> {

    String TABLE_NAME = "users";

    User getUserByLoginAndPassword(String login, String password);

    User getUserByMail(String mail);

    User getUserByLogin(String login);

    boolean addUserFriend(int userId, int friendId);

    void deleteUserFriend(int userId, int friendId);

    List<User> getUserFriends(int userId);

    List<User> searchUsersByLogin(String login);

    List<User> searchUsersByLogin(String login, Role role);

    List<User> getUsersPage(int limit, int offset);

    boolean checkUsersFriendship(int firstUserId, int secondUserId);

    void updateUserPhoto(int idImage, int userId);

    void updateUserStatus(int id, UserAccountStatus status);

    void increaseUserRating(int userId, int scorePlus);

    void changePassword(String password, String login);

    boolean appointToModer(int userId);

    boolean cancelAppointingToModer(int userId);

}
