package com.blog.topics.web.rest;


import com.blog.topics.model.User;
import com.blog.topics.service.TopicsService;
import com.blog.topics.service.UserService;
import com.blog.topics.to.UserTo;
import com.blog.topics.web.AuthorizedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import static com.blog.topics.util.ValidationUtil.assureIdConsistent;


@Slf4j
@RestController
@RequestMapping(UserRestProfileController.REST_URL)
public class UserRestProfileController {
    static final String REST_URL = "/profile/user/";

    final
    UserService userService;

    final
    TopicsService topicsService;


    @Autowired
    public UserRestProfileController(UserService userService, TopicsService topicsService) {
        this.userService = userService;
        this.topicsService = topicsService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User get(){
        int id = AuthorizedUser.id();
        log.info("get {}", id);
        return userService.getById(AuthorizedUser.id());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(){
        int id = AuthorizedUser.id();
        log.info("delete {}", id);
        userService.delete(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@RequestBody UserTo userTo){
        int id = AuthorizedUser.id();
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        userService.update(userTo);
    }
}
