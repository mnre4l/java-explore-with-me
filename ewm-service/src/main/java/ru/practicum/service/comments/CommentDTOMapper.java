package ru.practicum.service.comments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.api.comment.CommentDto;
import ru.practicum.jpa.entity.Comment;
import ru.practicum.service.mapper.DefaultMapper;

@Mapper(componentModel = "spring")
public interface CommentDTOMapper extends DefaultMapper<Comment, CommentDto> {
    @Override
    @Mapping(target = "authorLogin", source = "author.name")
    CommentDto toDTO(Comment comment);
}
