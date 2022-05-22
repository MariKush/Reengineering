package com.trustmenet.services;

import com.trustmenet.config.JWTUtils;
import com.trustmenet.mapper.UserMapper;
import com.trustmenet.repositories.dao.UserDao;
import com.trustmenet.repositories.dto.UserDto;
import com.trustmenet.repositories.entities.Token;
import com.trustmenet.repositories.entities.User;
import com.trustmenet.repositories.enums.Role;
import com.trustmenet.repositories.enums.TokenType;
import com.trustmenet.repositories.enums.UserAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@PropertySource("classpath:user.properties")
public class RegistrationService {

    @Value("#{${defaultImageId}}")
    private int defaultId;

    @Value("#{${recoveryUrl}}")
    private int recoveryUrl;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void registerUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        if (userDao.getUserByLogin(user.getLogin()) != null || userDao.getUserByMail(user.getMail()) != null) {
            return;
        }

        setDefaultUserProperties(user);

        int id = userDao.save(user);
        user.setId(id);
        Token tokenForNewUser = Token.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.REGISTRATION)
                .userId(id)
                .build();
        tokenService.saveToken(tokenForNewUser);
        mailService.sendRegistrationMessage(user.getMail(), user.getLogin(), "https://trust-me-net.herokuapp.com/#/registration/" + tokenForNewUser.getToken());
    }

    private void setDefaultUserProperties(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.USER);
        user.setStatus(UserAccountStatus.UNACTIVATED);

        user.setRating(0);
        user.setProfile("");
        user.setImageId(defaultId);
    }

    @Transactional
    public boolean openRegistrationToken(String tokenStr) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.REGISTRATION)
                .build();
        int id = tokenService.getUserId(token);
        if (id == 0) {
            return false;
        }
        tokenService.delete(token);
        User user = userDao.get(id);
        user.setStatus(UserAccountStatus.ACTIVATED);
        userDao.update(user);
        return true;
    }

    public String login(String login, String password) {
        User user = userDao.getUserByLogin(login);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())
                || user.getStatus().equals(UserAccountStatus.UNACTIVATED)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        return jwtUtils.generateToken(user.getId(), user.getRole(), user.getLogin());
    }

    public boolean passwordRecovery(String email) {
        User user = userDao.getUserByMail(email);
        if (user == null) {
            return false;
        }
        Token token = Token.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .userId(user.getId())
                .build();

        tokenService.saveToken(token);
        mailService.sendRecoveryPasswordMessage(user.getMail(), user.getLogin(), recoveryUrl + token.getToken());
        return true;
    }

    public boolean openPasswordRecoveryToken(String tokenStr) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .build();
        int id = tokenService.getUserId(token);
        return id != 0;
    }

    @Transactional
    public boolean editPassword(String tokenStr, String password) {
        Token token = Token.builder()
                .token(tokenStr)
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .build();
        int id = tokenService.getUserId(token);
        if (id == 0) {
            return false;
        }
        tokenService.delete(token);
        User user = userDao.get(id);
        user.setPassword(passwordEncoder.encode(password));
        userDao.update(user);
        return true;
    }
}
