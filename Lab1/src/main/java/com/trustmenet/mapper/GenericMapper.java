package com.trustmenet.mapper;


import java.util.List;

public interface GenericMapper<Dto, Entity> {
    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
    List<Dto> toDto(List<Entity> entities);
    List<Entity> toEntity(List<Dto> dtos);
}
