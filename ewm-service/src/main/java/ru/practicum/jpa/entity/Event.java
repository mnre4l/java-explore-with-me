package ru.practicum.jpa.entity;

import lombok.*;
import ru.practicum.service.event.states.State;
import ru.practicum.service.event.states.StateAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "event")
public class Event {
    @Column(name = "annotation")
    private String annotation;
    @ManyToOne
    private Category category;
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "description")
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User initiator;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "participant_limit")
    private Long participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(name = "title")
    private String title;
    @Column(name = "views")
    private Long views;
    @Embedded
    private Location location;
    @Enumerated(EnumType.STRING)
    private StateAction stateAction;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private List<Comment> comments;

    @Data
    @Embeddable
    public static class Location {
        private Double lat;
        private Double lon;
    }
}
