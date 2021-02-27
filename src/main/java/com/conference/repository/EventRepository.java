package com.conference.repository;

import com.conference.entity.Event;
import com.conference.dto.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, count(t.eventId)) FROM Event e left join Topic t on e.id = t.eventId GROUP by e.id")
    List<EventDTO> findEventDetails();

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, count(t.eventId)) FROM Event e left join Topic t on e.id = t.eventId GROUP by e.id")
    Page<EventDTO> findEventDetailsPaginated(PageRequest pageRequest);

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, count(t.eventId)) FROM Event e left join Topic t on e.id = t.eventId GROUP by e.id ORDER BY count(t.eventId) ASC")
    Page<EventDTO> findEventDetailsPaginatedSortByTopicCountAsc(PageRequest pageRequest);

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, count(t.eventId)) FROM Event e left join Topic t on e.id = t.eventId GROUP by e.id ORDER BY count(t.eventId) DESC")
    Page<EventDTO> findEventDetailsPaginatedSortByTopicCountDesc(PageRequest pageRequest);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_event (user_id, event_id) VALUES (:userId, :eventId)",
            nativeQuery = true)
    void assignUserToEvent(long userId, long eventId);
}
