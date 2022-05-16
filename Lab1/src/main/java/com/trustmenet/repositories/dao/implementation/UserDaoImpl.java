package com.trustmenet.repositories.dao.implementation;


import com.trustmenet.repositories.dao.UserDao;
import com.trustmenet.repositories.dao.mappers.UserMapper;
import com.trustmenet.repositories.entities.UserDto;
import com.trustmenet.repositories.entities.enums.Role;
import com.trustmenet.repositories.entities.enums.UserAccountStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@PropertySource("classpath:user.properties")
public class UserDaoImpl extends GenericDaoImpl<UserDto> implements UserDao {

    @Value("#{${sql.users}}")
    private Map<String, String> usersQueries;

    @Value("#{${sql.friends}}")
    private Map<String, String> friendsQueries;

    protected UserDaoImpl() {
        super(new UserMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return usersQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, UserDto userDto) throws SQLException {
        preparedStatement.setString(1, userDto.getLogin());
        preparedStatement.setString(2, userDto.getPassword());
        preparedStatement.setString(3, userDto.getMail());
        preparedStatement.setString(4,
                Optional.ofNullable(userDto.getStatus()).orElse(UserAccountStatus.UNACTIVATED)
                        .name().toLowerCase());
        preparedStatement.setString(5,
                Optional.ofNullable(userDto.getRole()).orElse(Role.USER)
                        .name().toLowerCase());
        preparedStatement.setString(6, userDto.getFirstName());
        preparedStatement.setString(7, userDto.getSecondName());
        if (userDto.getProfile() != null) {
            preparedStatement.setString(8, userDto.getProfile());
        } else {
            preparedStatement.setNull(8, Types.VARCHAR);
        }
        preparedStatement.setInt(9, userDto.getRating());
        preparedStatement.setInt(10, userDto.getImageId());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return usersQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(UserDto userDto) {
        return new Object[]{userDto.getLogin(), userDto.getPassword(), userDto.getMail(),
                userDto.getStatus().name().toLowerCase(), userDto.getRole().name().toLowerCase(),
                userDto.getFirstName(), userDto.getSecondName(), userDto.getRegistrationDate(),
                userDto.getProfile(), userDto.getRating(), userDto.getImageId(),
                userDto.getId()};
    }

    @Override
    public List<UserDto> getAll() {
        return jdbcTemplate.query(usersQueries.get("getAllUsers"), new UserMapper());
    }

    @Override
    public UserDto get(int id) {
        UserDto userDto;
        try {
            userDto = jdbcTemplate.queryForObject(usersQueries.get("getUser"),
                    new Object[]{id}, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return userDto;
    }

    @Override
    public UserDto getUserByLoginAndPassword(String login, String password) {
        UserDto userDto;
        try {
            userDto = jdbcTemplate.queryForObject(usersQueries.get("selectByLoginAndPassword"),
                    new Object[]{login, password}, new UserMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return userDto;
    }

    @Override
    public UserDto getUserByMail(String mail) {
        UserDto userDto;
        try {
            userDto = jdbcTemplate.queryForObject(usersQueries.get("selectByMail"),
                    new Object[]{mail}, new UserMapper());
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return null;
        }
        return userDto;
    }

    @Override
    public UserDto getUserByLogin(String login) {
        UserDto userDto;
        try {
            userDto = jdbcTemplate.queryForObject(usersQueries.get("selectByLogin"),
                    new Object[]{login}, new UserMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return userDto;
    }

    @Override
    public boolean addUserFriend(int userId, int friendId) {
        try {
            jdbcTemplate.update(
                    friendsQueries.get("addUserFriend"),
                    userId, friendId
            );
        } catch (DuplicateKeyException e) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteUserFriend(int userId, int friendId) {
        jdbcTemplate.update(
                friendsQueries.get("deleteUserFriend"),
                userId, friendId
        );
    }

    @Override
    public List<UserDto> getUserFriends(int userId) {
        return jdbcTemplate.query(
                friendsQueries.get("getUserFriends"),
                new Object[]{userId}, new UserMapper()
        );
    }

    @Override
    public List<UserDto> searchUsersByLogin(String login) {
        login = '%' + login + '%';
        return jdbcTemplate.query(
                usersQueries.get("searchUsersByLogin"),
                new Object[]{login}, new UserMapper()
        );
    }

    @Override
    public List<UserDto> searchUsersByLogin(String login, Role role) {
        login = '%' + login + '%';
        return jdbcTemplate.query(
                usersQueries.get("searchUsersByLogin").replace(";", " AND role = cast(? AS user_role);"),
                new Object[]{login, role.name().toLowerCase()}, new UserMapper()
        );
    }

    @Override
    public List<UserDto> getUsersPage(int limit, int offset) {
        return jdbcTemplate.query(
                usersQueries.get("getNextUserPage"),
                new Object[]{limit, offset}, new UserMapper()
        );
    }

    @Override
    public boolean checkUsersFriendship(int firstUserId, int secondUserId) {
        Integer counter = jdbcTemplate.queryForObject(friendsQueries.get("checkFriendship"),
                new Object[]{firstUserId, secondUserId},
                (resultSet, number) -> resultSet.getInt("row_count"));
        return counter != null && counter > 0;
    }

    @Override
    public void updateUserStatus(int id, UserAccountStatus status) {
        jdbcTemplate.update(usersQueries.get("updateUserStatus"),
                status.name().toLowerCase(), id);
    }

    @Override
    public void increaseUserRating(int userId, int scorePlus) {
        log.info("update");
        jdbcTemplate.update(usersQueries.get("increaseRating"), scorePlus, userId);
    }

    @Override
    public void changePassword(String password, String login) {
        jdbcTemplate.update(usersQueries.get("changePassword"),
                password, login);
    }

    @Override
    public boolean appointToModer(int userId) {
        jdbcTemplate.update(usersQueries.get("appointToModer"), userId);
        return true;
    }

    @Override
    public boolean cancelAppointingToModer(int userId) {
        jdbcTemplate.update(usersQueries.get("cancelAppointingToModer"), userId);
        return true;
    }

    @Override
    public void updateUserPhoto(int imageId, int userId) {
        jdbcTemplate.update(usersQueries.get("updateUserPhoto"), imageId, userId);
    }
}
