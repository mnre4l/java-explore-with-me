package ru.practicum.api.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.jpa.entity.Event;
import ru.practicum.service.event.validation.NoLaterThanTwoHours;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewEventDto {
    @Size(min = 20, max = 2000, message = "Annotation должно быть больше 20 и меньше 2000 символов")
    @NotBlank
    private String annotation;
    @NotNull
    @Positive
    private Long category;
    @Size(min = 20, max = 7000, message = "Description должно быть больше 20 и меньше 7000 символов")
    @NotBlank
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NoLaterThanTwoHours
    private LocalDateTime eventDate;
    @NotNull
    private Event.Location location;
    private Boolean paid = false;
    private Long participantLimit = 0L;
    private Boolean requestModeration = true;
    @Size(min = 3, max = 120, message = "Title должно быть больше 20 и меньше 7000 символов")
    @NotBlank
    private String title;
}
