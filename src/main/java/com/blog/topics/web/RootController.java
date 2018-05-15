package com.blog.topics.web;

import com.blog.topics.model.Topics;
import com.blog.topics.model.User;
import com.blog.topics.service.TopicsService;
import com.blog.topics.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class RootController {

    private static final String REST_URL = "/rest/profile/user/";

    @Autowired
    UserService userService;

    @Autowired
    TopicsService topicsService;

    @GetMapping("/")
    public String root(){
        return "redirect:/topics";
    }

    @GetMapping(value = "/topics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Topics> getAllTopics(){
        log.info("Get All Topics");
        return topicsService.getAllForAllUsers();
    }
//TODO topics{id}
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getAllUsers(){
        log.info("Get All Users");
        return userService.getAll();
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUserById(@PathVariable("userId") int userId){
        log.info("Get Users By id");
        return userService.getById(userId);
    }

    @GetMapping(value = "/users/{userId}/topics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Topics> getUserAllTopics(@PathVariable("userId") int userId){
        log.info("Get All Users's topics");
        return topicsService.getAll(userId);
    }

    @GetMapping(value = "/users/{userId}/topics/{topicId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Topics getUserTopicById(@PathVariable("userId") int userId, @PathVariable("topicId") int topicId){
        log.info("Get All Users's topics");
        return topicsService.get(topicId, userId);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        User created = userService.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @RequestMapping(value = "/logmeout", method = RequestMethod.POST)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }


}
