package com.conference.controller;

import com.conference.entity.Event;
import com.conference.entity.Topic;
import com.conference.entity.User;
import com.conference.service.EventService;
import com.conference.service.TopicService;
import com.conference.service.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    private final TopicService topicService;

    private final UserDetailsServiceImpl userDetailsService;

    public EventController(EventService eventService, TopicService topicService, UserDetailsServiceImpl userDetailsService) {
        this.eventService = eventService;
        this.topicService = topicService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/new")
    public String viewNewEventPage(Model model) {
        Event event = new Event();
        model.addAttribute("event", event);

        return "create_event";
    }


    @PostMapping("/save")
    public String saveEvent(@ModelAttribute("event") Event event) {
        eventService.save(event);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getEventDetails(Model model, @PathVariable int id) {
        User user = getAuthenticatedUser();
        boolean showApprovedOnly = true;
        boolean assigned = false;

        if (user != null) {
            assigned = eventService.isUserAssigned(user.getId(),id);
            String role = user.getAuthorities().iterator().next().getAuthority();
            final String ROLE_ADMIN = "ROLE_ADMIN";

            if (role.equals(ROLE_ADMIN)) {
                showApprovedOnly = false;
            }
        }

        Topic topic = new Topic();
        List<Topic> eventTopics = showApprovedOnly ?
                topicService.getApprovedTopics(id, true) :
                topicService.getEventTopics(id);

        String speaker = userDetailsService.findById(7L).getUsername();

        model.addAttribute("event", eventService.get(id));
        model.addAttribute("eventTopics", eventTopics);
        model.addAttribute("topic", topic);
        model.addAttribute("speaker", speaker);
        model.addAttribute("assigned", assigned);

        return "event_details";
    }

    @GetMapping("/edit/{id}")
    public String getEditEventForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("event", eventService.get(id));

        return "event_edit";
    }

    @PostMapping("/edit/{id}")
    public String editEvent(@ModelAttribute("event") Event event,
                            @PathVariable int id) {
        eventService.update(id, event);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id) {
        eventService.delete(id);

        return "redirect:/";
    }

    @PostMapping("/assign/{eventId}")
    public String assignUserToEvent(@PathVariable("eventId") int eventId) {
        User user = getAuthenticatedUser();

        if (user != null) {
            eventService.assignUserToEvent(eventId, user.getId());
        }

        return "redirect:/";
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return (User) authentication.getPrincipal();
        }

        return null;
    }
}
