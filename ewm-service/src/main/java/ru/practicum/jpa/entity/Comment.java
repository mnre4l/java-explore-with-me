package ru.practicum.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(schema = "public", name = "comment")
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "event_id")
    private Long eventId;
    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;
}
