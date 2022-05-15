package com.trustmenet.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Configuration
public class MailConfiguration {

    @Bean
    public Session emailSession(SessionAuthenticator sessionAuthenticator) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("mail/application-mail-config.properties"));
        } catch (IOException e) {
            log.error("Failed to load email session properties", e);
        }
        return Session.getInstance(properties, sessionAuthenticator);
    }

}
