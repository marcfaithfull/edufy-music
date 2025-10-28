package org.example.microservicemusic.model.entity;

import jakarta.persistence.*;
import org.example.microservicemusic.model.enumeration.Reaction;

@Entity
@Table(name = "user_song_reaction")
public class UserSongReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction")
    private Reaction reaction;

    public UserSongReaction() {
        this.reaction = Reaction.NEUTRAL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }
}
