package ru.practicum.api.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewCommentDto {
    @NotBlank
    private String text;
    @NotNull
    private Long eventId;
}
