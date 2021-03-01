package com.conference.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_event")
@IdClass(UserEventId.class)
public class UserEvent {
    @Id
    private Long userId;
    @Id
    private Long eventId;
    private boolean visited;
}
