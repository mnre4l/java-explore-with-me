package ru.practicum.api.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String authorLogin;
    private Long eventId;
    private String text;
    private LocalDateTime createdAt;
}
