package com.blog.topics.model;

import com.blog.topics.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SuppressWarnings("JpaQlInspection")
//@NamedQueries({
//        @NamedQuery(name = Topics.ALL_SORTED, query = "SELECT m FROM Topics m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
//        @NamedQuery(name = Topics.DELETE, query = "DELETE FROM Topics m WHERE m.id=:id AND m.user.id=:userId"),
//        @NamedQuery(name = Topics.GET_BETWEEN, query = "SELECT m FROM Topics m " +
//                "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC"),
////        @NamedQuery(name = Topics.UPDATE, query = "UPDATE Topics m SET m.dateTime = :datetime, m.text= :text," +
////                "m.description=:desc where m.id=:id and m.user.id=:userId")
//})
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@Id", scope = Topics.class)
@Table(name = "topics", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "topics_unique_user_datetime_idx")})
public class Topics extends AbstractBaseEntity {
//
//    public static final String ALL_SORTED = "Topics.getAll";
//    public static final String DELETE = "Topics.delete";
//    public static final String GET_BETWEEN = "Topics.getBetween";

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "text", nullable = false)
    @Size(min = 2, max = 120)
    @NotNull
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Topics() {
    }

    public Topics(LocalDateTime dateTime, String description, String text) {
        this(null, dateTime, description, text);
    }

    public Topics(Integer id, LocalDateTime dateTime, String description, String text) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setText(String text) {
        this.text = text;
    }

//    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Topics{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", text=" + text +
                '}';
    }
}
