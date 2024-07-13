package com.ghtk.social_network.infrastracture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_friend")
    private Boolean isFriend;

    @Lob
    @Column(name = "other_relation")
    private String otherRelation;

    @Column(name = "created_at")
    private Instant createdAt;

}