package ru.practicum.service.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.api.comment.CommentDto;
import ru.practicum.api.comment.NewCommentDto;
import ru.practicum.api.page.Page;
import ru.practicum.exception.ForbiddenToChangeException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.jpa.entity.Comment;
import ru.practicum.jpa.entity.Event;
import ru.practicum.jpa.entity.User;
import ru.practicum.jpa.repository.EventCommentsRepository;
import ru.practicum.jpa.repository.EventRepository;
import ru.practicum.jpa.repository.UserRepository;
import ru.practicum.service.event.states.State;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiEventCommentService implements EventCommentService {
    private final EventCommentsRepository commentsRepository;
    private final CommentDTOMapper mapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto create(Long userRequestFromId, NewCommentDto newCommentDto) {
        User author = userRepository.findById(userRequestFromId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь id = " + userRequestFromId));
        Event event = eventRepository.findById(newCommentDto.getEventId())
                .orElseThrow(() -> new NotFoundException("Не найден event id = " + newCommentDto.getEventId()));

        handleEventCanBeCommented(event);

        Comment comment = Comment.builder()
                .eventId(newCommentDto.getEventId())
                .author(author)
                .createdAt(LocalDateTime.now())
                .text(newCommentDto.getText())
                .build();

        commentsRepository.save(comment);
        return mapper.toDTO(comment);
    }

    @Override
    public List<CommentDto> getCommentsByEvent(Long eventId, Integer from, Integer size) {
        List<Comment> comments = Optional.ofNullable(eventId)
                .map(id -> eventRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Не найден event id = " + id)))
                .map(event -> commentsRepository.findAllByEventId(event.getId(), new Page(from, size, Sort.unsorted())))
                .orElse(Collections.emptyList());

        return List.copyOf(mapper.toDTO(comments));
    }

    private void handleEventCanBeCommented(Event event) {
        Optional.ofNullable(event)
                // ивент не находится в ожидании публикации, т.е. или опубликован или уже прошел
                .filter(e -> !e.getState().equals(State.PENDING))
                .orElseThrow(() -> new ForbiddenToChangeException("Комментарии к ивенту запрещены"));
    }
}
