package com.epam.esm.entity;

import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "login")
@ToString(exclude = "orders")
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
/*    @EqualsAndHashCode.Exclude
    @ToString.Exclude*/
//    @Builder.Default
//    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
//    private List<OrderEntity> orderEntityList;
}
