package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString (exclude = "users")
@EqualsAndHashCode(exclude = "users")
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "roleEntity", fetch = FetchType.LAZY)
    private List<UserEntity> users;
}
