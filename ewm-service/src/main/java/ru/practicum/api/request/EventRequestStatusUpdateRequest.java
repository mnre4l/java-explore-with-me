package ru.practicum.api.request;

import lombok.Data;
import ru.practicum.service.request.Status;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    private Status status;
    private List<Long> requestIds;
}
