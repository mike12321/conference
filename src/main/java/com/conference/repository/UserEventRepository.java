package com.conference.repository;

import com.conference.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
    boolean existsByUserIdAndAndEventId(long userId, long eventId);
}
