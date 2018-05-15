package com.blog.topics.service;

import com.blog.topics.model.Topics;
import com.blog.topics.repository.TopicsRepository;
import com.blog.topics.repository.UserRepository;
import com.blog.topics.web.AuthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.blog.topics.util.ValidationUtil.checkNotFoundWithId;


@Service
public class TopicsServiceImpl implements TopicsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TopicsRepository topicsRepository;

    @Override
    @Transactional
    public Topics create(Topics topics, int userId){
        Assert.notNull(topics, "topics must not be null");

        if (!topics.isNew() && get(topics.getId(), userId) == null) {
            return null;
        }
        topics.setUser(userRepository.getOne(userId));

        return topicsRepository.save(topics);
    }

    @Override
    public Topics get(int id, int userId) {
        Topics topics = topicsRepository.findById(id).filter(topic ->
        topic.getUser().getId() == userId).orElse(null);

        return checkNotFoundWithId(topics, id);
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(topicsRepository.delete(id, userId), id);
    }

    @Override
    public List<Topics> getAll(int userId) {
        return topicsRepository.getAll(userId);
    }

    @Override
    public List<Topics> getAllForAllUsers() {
        return topicsRepository.findAll();
    }

    @Override
    public Topics update(Topics topics, int userId) {
        return checkNotFoundWithId(create(topics, userId), topics.getId());
    }

    @Override
    public Topics get(int id) {
        return topicsRepository.getOne(id);
    }


}
