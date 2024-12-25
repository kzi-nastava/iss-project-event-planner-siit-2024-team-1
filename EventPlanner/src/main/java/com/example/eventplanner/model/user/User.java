package com.example.eventplanner.model.user;
import com.example.eventplanner.model.auth.Role;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Merchandise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // All subclasses stored in one table
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "\"user\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @TableGenerator(
            name = "user_gen",
            table = "id_generator",
            pkColumnName = "sequence_name",
            valueColumnName = "next_val"
    )
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "user_gen")
    private int id;

    private String name;
    private String surname;
    private String phoneNumber;

    @Embedded
    private Address address;

    private String username;
    private String password;
    private String photo;
    private Role role;
    private String authorities;

    private boolean active = false;
    private String activationToken;
    private Date tokenExpiration;


    @ManyToMany
    @JoinTable(name = "user_blocked_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id"))
    private List<User> blockedUsers;

    @OneToMany
    private List<Notification> notifications;

    @ManyToMany
    @JoinTable(name = "user_attended_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> attendedEvents;

    @ManyToMany
    @JoinTable(name = "user_favorite_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> favoriteEvents;

    @ManyToMany
    @JoinTable(name = "user_followed_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> followedEvents;

    @ManyToMany
    @JoinTable(name= "user_favorite_merchandises",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "merchandise_id"))
    private List<Merchandise> favoriteMerchandises;

}
