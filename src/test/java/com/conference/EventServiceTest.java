package com.conference;

import com.conference.entity.Event;
import com.conference.repository.EventRepository;
import com.conference.service.EventService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EventServiceTest {
    private Event event = new Event();

    @MockBean
    private EventService service;
    
    @MockBean
    private EventRepository eventRepository;

    @Before
    public void setUp() throws Exception {
        event.setTitle("testEvent132");
        event.setDateTime(LocalDateTime.now());

        service.save(event);
        Mockito.when(eventRepository.findByTitle(event.getTitle()))
                .thenReturn(event);
    }

    @Test
    public void findByName() throws Exception {
        Event found = eventRepository.findByTitle(event.getTitle());

        assertThat(found.getTitle())
                .isEqualTo(event.getTitle());
    }

    @After
    public void cleanUp() {
        eventRepository.removeByTitle(event.getTitle());
    }
}
