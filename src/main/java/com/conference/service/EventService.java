package com.conference.service;

import com.conference.entity.Event;
import com.conference.dto.EventDTO;
import com.conference.repository.EventRepository;
import com.conference.repository.UserEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserEventRepository userEventRepository;

    public EventService(EventRepository eventRepository, UserEventRepository userEventRepository) {
        this.eventRepository = eventRepository;
        this.userEventRepository = userEventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    public Event get(long id) {
        return eventRepository.findById(id).get();
    }

    public void update(long id, Event event) {
        Event eventToUpdate = eventRepository.getOne(id);

        eventToUpdate.setTitle(event.getTitle());
        eventToUpdate.setDateTime(event.getDateTime());
        save(eventToUpdate);
    }

    public void delete(long id) {
        eventRepository.deleteById(id);
    }

    public List<EventDTO> findEventDetails() {
        return eventRepository.findEventDetails();
    }

    public Page<EventDTO> findEventDetailsPaginated(int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        PageRequest pageable = PageRequest.of(page - 1, size, sort);

        if (sortField.equals("topicCount")) {
            return findEventDetailsSortedByTopicCount(page, size, sortDirection);
        }

        if (sortField.equals("participantsCount")) {
            return findEventDetailsPaginatedSortByParticipantsCount(page, size, sortDirection);
        }

        return eventRepository.findEventDetailsPaginated(pageable);
    }

    public Page<EventDTO> findEventDetailsSortedByTopicCount(int page, int size, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        if (sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())) {
            return eventRepository.findEventDetailsPaginatedSortByTopicCountAsc(pageRequest);
        }

        return eventRepository.findEventDetailsPaginatedSortByTopicCountDesc(pageRequest);
    }

    public Page<EventDTO> findEventDetailsPaginatedSortByParticipantsCount(int page, int size, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        if (sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())) {
            return eventRepository.findEventDetailsPaginatedSortByParticipantsCountAsc(pageRequest);
        }

        return eventRepository.findEventDetailsPaginatedSortByParticipantsCountDesc(pageRequest);
    }

    public void assignUserToEvent(long userId, long eventId) {
        eventRepository.assignUserToEvent(userId, eventId);
    }

    public boolean isUserAssigned(long userId, long eventId) {
        return userEventRepository.existsByUserIdAndAndEventId(userId, eventId);
    }
}
