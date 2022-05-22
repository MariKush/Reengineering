package com.trustmenet.mapper;


import com.trustmenet.repositories.dto.MessageDto;
import com.trustmenet.repositories.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper extends GenericMapper<MessageDto, Message> {

    default Page<MessageDto> toDto(Page<Message> messages) {
        return new PageImpl<>(this.toDto(messages.getContent()));
    }
}
