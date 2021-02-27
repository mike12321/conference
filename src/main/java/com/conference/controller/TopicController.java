package com.conference.controller;

import com.conference.entity.Topic;
import com.conference.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/add/{id}")
    public String addTopic(@ModelAttribute("topic") Topic topic,
                           @PathVariable("id") int id) {
        topicService.save(topic, id);

        return "redirect:/event/" + id;
    }

//    @PatchMapping("/approve") TODO: figure out why PATCH get treated like POST ignoring th:method
    @PostMapping("/edit")
    public String setApproved(@ModelAttribute("topic") Topic topic) {
        topicService.update(topic.getId(), topic);

        return "redirect:/event/" + topic.getEventId();
    }
}
