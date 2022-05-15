package com.trustmenet.repositories.dto;

import com.trustmenet.repositories.entities.Message;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebsocketEvent {
    private EventType type;
    private Message message;

    public enum EventType {
        MESSAGE,
        NOTIFICATION
    }
}
