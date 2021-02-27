package com.conference.service;

import com.conference.entity.Topic;
import com.conference.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public void save(Topic topic, int eventId) {
        Topic newTopic = Topic.builder()
                .eventId((long) eventId)
                .title(topic.getTitle())
                .approved(topic.isApproved())
                .build();

        topicRepository.save(newTopic);
    }

    public List<Topic> getEventTopics(long eventId) {
        return topicRepository.findByEventId(eventId);
    }

    public List<Topic> getApprovedTopics(long eventId, boolean approved) {
        return topicRepository.findByEventIdAndAndApproved(eventId, approved);
    }

    public void update(long topicId, Topic topic) {
        Topic topicToUpdate = topicRepository.getOne(topicId);

        topic.setApproved(topic.isApproved());
        topic.setSpeakerId(topic.getSpeakerId());

        topicRepository.save(topicToUpdate);
    }
}
