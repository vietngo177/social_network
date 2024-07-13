package com.ghtk.social_network.infrastracture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "chat_members")
public class ChatMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "join_at")
    private Instant joinAt;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}