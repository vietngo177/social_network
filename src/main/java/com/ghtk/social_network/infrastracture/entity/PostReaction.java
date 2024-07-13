package com.ghtk.social_network.infrastracture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "post_reactions")
public class PostReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "reaction_type")
    private String reactionType;

    @Column(name = "created_at")
    private Instant createdAt;

}