package com.blog.topics.web.rest;

import com.blog.topics.model.Topics;
import com.blog.topics.repository.TopicsRepository;
import com.blog.topics.service.TopicsService;
import com.blog.topics.web.AuthorizedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.blog.topics.util.ValidationUtil.assureIdConsistent;
import static com.blog.topics.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = TopicsRestProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TopicsRestProfileController {
    static final String REST_URL = "/profile/user/topics";

    @Autowired
    TopicsService topicsService;

    @GetMapping("/{id}")
    public Topics get(@PathVariable("id") int id){
        int userId = AuthorizedUser.id();
        log.info("get topic {} for user {}", id, userId);
        return topicsService.get(id, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        int userId = AuthorizedUser.id();
        log.info("delete topic {} for user {}", id, userId);
        topicsService.delete(id, userId);
    }

    @GetMapping
    public List<Topics> getAll(){
        int userId = AuthorizedUser.id();
        log.info("getAll for user {}", userId);
        return topicsService.getAll(userId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Topics topics, @PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        assureIdConsistent(topics, id);
        log.info("update {} for user {}", topics, userId);
        topicsService.update(topics, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Topics> createWithLocation(@RequestBody Topics topics) {
        int userId = AuthorizedUser.id();
        checkNew(topics);
        log.info("create {} for user {}", topics, userId);

        Topics created = topicsService.create(topics, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
