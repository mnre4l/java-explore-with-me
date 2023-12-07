package ru.practicum.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Table(schema = "public", name = "category")
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
}
