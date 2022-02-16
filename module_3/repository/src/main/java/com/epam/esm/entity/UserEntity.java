package com.epam.esm.entity;

import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
