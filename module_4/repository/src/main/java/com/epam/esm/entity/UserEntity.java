package com.epam.esm.entity;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleEntity")
@EqualsAndHashCode(exclude = "roleEntity")
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    public static final String val = "2";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;
}
