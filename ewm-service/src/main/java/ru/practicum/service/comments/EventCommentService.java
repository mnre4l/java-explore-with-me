package ru.practicum.service.comments;

import ru.practicum.api.comment.CommentDto;
import ru.practicum.api.comment.NewCommentDto;
import ru.practicum.service.crud.byuser.UserRequestCreateService;

import java.util.List;

public interface EventCommentService extends UserRequestCreateService<CommentDto, Long, NewCommentDto> {
    List<CommentDto> getCommentsByEvent(Long eventId, Integer from, Integer size);
}
