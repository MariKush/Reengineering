package com.trustmenet.services;

import com.trustmenet.config.JWTUtils;
import com.trustmenet.repositories.dao.UserDao;
import com.trustmenet.repositories.entities.Token;
import com.trustmenet.repositories.entities.UserDto;
import com.trustmenet.repositories.entities.enums.Role;
import com.trustmenet.repositories.entities.enums.TokenType;
import com.trustmenet.repositories.entities.enums.UserAccountStatus;
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

    @Transactional
    public void registerUser(UserDto user) {
        if (userDao.getUserByLogin(user.getLogin()) != null || userDao.getUserByMail(user.getMail()) != null) {
            return;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.USER);
        user.setStatus(UserAccountStatus.UNACTIVATED);

        user.setRating(0);
        user.setProfile("");
        user.setImageId(defaultId);

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
        UserDto user = userDao.get(id);
        user.setStatus(UserAccountStatus.ACTIVATED);
        userDao.update(user);
        return true;
    }

    public String login(String login, String password) {
        UserDto user = userDao.getUserByLogin(login);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())
                || user.getStatus().equals(UserAccountStatus.UNACTIVATED)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        return jwtUtils.generateToken(user.getId(), user.getRole(), user.getLogin());
    }

    public boolean passwordRecovery(String email) {
        UserDto user = userDao.getUserByMail(email);
        if (user == null) {
            return false;
        }
        Token token = Token.builder()
                .token(UUID.randomUUID().toString())
                .tokenType(TokenType.PASSWORD_RECOVERY)
                .userId(user.getId())
                .build();

        tokenService.saveToken(token);
        mailService.sendRecoveryPasswordMessage(user.getMail(), user.getLogin(), "https://trust-me-net.herokuapp.com/#/recovery/" + token.getToken());
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
        UserDto user = userDao.get(id);
        user.setPassword(passwordEncoder.encode(password));
        userDao.update(user);
        return true;
    }
}
