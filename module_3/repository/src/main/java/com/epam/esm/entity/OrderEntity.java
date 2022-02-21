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
    private Double cost;
    private LocalDateTime date;
    //    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @Builder.Default
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    //    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @Builder.Default
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "certificate_id")
//    private CertificateEntity certificateEntity;
    @OneToMany(mappedBy = "orderEntity"/*, fetch = FetchType.LAZY*/)
    private List<OrderCertificateEntity> orderCertificateEntityList;
}
