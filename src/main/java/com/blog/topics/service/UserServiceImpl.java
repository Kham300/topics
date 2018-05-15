package com.blog.topics.service;

import com.blog.topics.model.User;
import com.blog.topics.repository.UserRepository;
import com.blog.topics.to.UserTo;
import com.blog.topics.util.UserUtil;
import com.blog.topics.util.errors.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.blog.topics.util.ValidationUtil.checkNotFound;
import static com.blog.topics.util.ValidationUtil.checkNotFoundWithId;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByName(username);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    @Override
    public void update(UserTo userTo) {
        User user = getById(userTo.getId());
        userRepository.save(UserUtil.updateFromTo(user, userTo));
    }

    @Override
    public User getById(int id) {
        log.info("In UserServiceImpl getById {}", id);
        return checkNotFoundWithId(userRepository.getOne(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");

        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    @Override
    public void delete(int id) {
        log.info("In UserServiceImpl delete {}", id);
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
