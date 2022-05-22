package com.trustmenet.repositories.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.Arrays.asList;

public enum MessageInfo {
    registration(new MessageInfoItem("mail/registration.en.html", "Confirm registration on TrustMeNet")),
    passwordRecover(new MessageInfoItem("mail/passwordRecovery.en.html", "Confirm reset password on TrustMeNet"));

    private final List<MessageInfoItem> items;

    MessageInfo(MessageInfoItem... items) {
        this.items = asList(items);
    }

    public MessageInfoItem getItem() {
        return items.get(0);
    }

    @Data
    @AllArgsConstructor
    public static class MessageInfoItem {
        private final String filename;
        private final String subject;
    }
}
