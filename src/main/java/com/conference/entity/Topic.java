package com.conference.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotEmpty(message = "Title can't be empty")
    private String title;

    @Column
    private Long speakerId;

    @Column
    @NotEmpty
    private Long eventId;

    @Column
    private boolean approved;
}
