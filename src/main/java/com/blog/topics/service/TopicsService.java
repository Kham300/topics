package com.blog.topics.service;

import com.blog.topics.model.Topics;
import com.blog.topics.util.errors.NotFoundException;

import java.util.List;

public interface TopicsService {

    Topics get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    List<Topics> getAll(int userId);

    List<Topics> getAllForAllUsers();

    Topics create(Topics topics, int userId);

    Topics update(Topics topics, int userId) throws NotFoundException;

    Topics get(int id);
}
