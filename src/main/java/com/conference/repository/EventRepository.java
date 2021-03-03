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

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, COUNT(t.eventId), COUNT(DISTINCT ue.userId)) " +
            "FROM Event e " +
            "LEFT JOIN Topic t ON e.id = t.eventId " +
            "LEFT JOIN UserEvent ue ON e.id = ue.eventId " +
            "GROUP BY e.id")
    List<EventDTO> findEventDetails();

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, COUNT(t.eventId), COUNT(DISTINCT ue.userId)) " +
            "FROM Event e " +
            "LEFT JOIN Topic t ON e.id = t.eventId " +
            "LEFT JOIN UserEvent ue ON e.id = ue.eventId " +
            "GROUP BY e.id")
    Page<EventDTO> findEventDetailsPaginated(PageRequest pageRequest);

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, COUNT(t.eventId), COUNT(DISTINCT ue.userId)) " +
            "FROM Event e " +
            "LEFT JOIN Topic t ON e.id = t.eventId " +
            "LEFT JOIN UserEvent ue ON e.id = ue.eventId " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(t.eventId) ASC")
    Page<EventDTO> findEventDetailsPaginatedSortByTopicCountAsc(PageRequest pageRequest);

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, COUNT(t.eventId), COUNT(DISTINCT ue.userId)) " +
            "FROM Event e " +
            "LEFT JOIN Topic t ON e.id = t.eventId " +
            "LEFT JOIN UserEvent ue ON e.id = ue.eventId " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(t.eventId) DESC")
    Page<EventDTO> findEventDetailsPaginatedSortByTopicCountDesc(PageRequest pageRequest);

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, COUNT(t.eventId), COUNT(DISTINCT ue.userId)) " +
            "FROM Event e " +
            "LEFT JOIN Topic t ON e.id = t.eventId " +
            "LEFT JOIN UserEvent ue ON e.id = ue.eventId " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(DISTINCT ue.userId) ASC")
    Page<EventDTO> findEventDetailsPaginatedSortByParticipantsCountAsc(PageRequest pageRequest);

    @Query("SELECT new com.conference.dto.EventDTO(e.id, e.title, e.dateTime, COUNT(t.eventId), COUNT(DISTINCT ue.userId)) " +
            "FROM Event e " +
            "LEFT JOIN Topic t ON e.id = t.eventId " +
            "LEFT JOIN UserEvent ue ON e.id = ue.eventId " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(DISTINCT ue.userId) DESC")
    Page<EventDTO> findEventDetailsPaginatedSortByParticipantsCountDesc(PageRequest pageRequest);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_event (user_id, event_id) VALUES (:userId, :eventId)",
            nativeQuery = true)
    void assignUserToEvent(long userId, long eventId);

    Event findByTitle(String title);

    boolean removeByTitle(String title);
}
