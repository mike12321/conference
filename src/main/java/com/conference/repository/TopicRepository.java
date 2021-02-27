package com.conference.repository;

import com.conference.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByEventId(long eventId);

    List<Topic> findByEventIdAndAndApproved(long eventId, boolean approved);

    @Modifying
    @Query("UPDATE Topic t set t.approved = :approved where t.id = :topicId")
    @Transactional
    void setApproved(long topicId, boolean approved);
}
