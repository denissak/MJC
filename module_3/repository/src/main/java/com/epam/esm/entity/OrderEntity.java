package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderCertificateEntityList", "userEntity"})
@ToString(exclude = {"orderCertificateEntityList", "userEntity"})
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double cost;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @OneToMany(mappedBy = "orderEntity")
    private List<OrderCertificateEntity> orderCertificateEntityList;
}
