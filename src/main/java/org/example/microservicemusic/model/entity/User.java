package org.example.microservicemusic.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    /*@OneToMany(mappedBy = "user")
    private List<UserSongReaction> reactions;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String firstName) {
        this.username = firstName;
    }

    /*public List<UserSongReaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<UserSongReaction> reactions) {
        this.reactions = reactions;
    }*/
}
