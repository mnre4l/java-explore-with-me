package ru.practicum.api.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.jpa.entity.Event;
import ru.practicum.service.event.states.StateAction;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000, message = "Annotation должно быть меньше 20 и больше 2000")
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = "Description должно быть меньше 20 и больше 2000")
    private String description;
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Event.Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120, message = "Title должен быть больше 3 и меньшк 120")
    private String title;
}
