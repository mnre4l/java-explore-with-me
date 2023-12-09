package ru.practicum.jpa.entity;

import lombok.*;
import ru.practicum.service.request.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "event_participation_request")
public class EventParticipationRequest {
    @Column(name = "created")
    private LocalDateTime created;
    @ManyToOne
    private Event event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User requester;
    @Enumerated(EnumType.STRING)
    private Status status;
}
