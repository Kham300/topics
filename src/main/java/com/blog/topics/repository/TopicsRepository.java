package com.blog.topics.repository;

import com.blog.topics.model.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface TopicsRepository extends JpaRepository<Topics, Integer> {

    @Override
    Optional<Topics> findById(Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Topics m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Topics save(Topics item);

    @Query("SELECT m FROM Topics m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Topics> getAll(@Param("userId") int userId);

//    @Query("SELECT m FROM Topics m JOIN FETCH m.user WHERE m.id = ?1 and m.user.id = ?2")
//    Topics getWithUser(int id, int userId);

    @Override
    List<Topics> findAll();
}
