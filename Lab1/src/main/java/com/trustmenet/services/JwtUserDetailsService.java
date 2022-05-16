package com.trustmenet.services;

import com.trustmenet.repositories.dao.UserDao;
import com.trustmenet.repositories.entities.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userDao.getUserByLogin(username);
        UserDetails userDetails = null;
        try {
            if (user.getLogin().equals(username)) {
                userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
            }
        } catch (NullPointerException npe) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return userDetails;
    }

    private Collection<SimpleGrantedAuthority> getAuthority(UserDto user) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
}
