package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tagEntities","orderCertificateEntityList"})
@ToString(exclude = {"tagEntities","orderCertificateEntityList"})
@Builder
@Entity
@Table(name = "gift_certificate")
public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime lastUpdateDate;
    private LocalDateTime createDate;
//    @Builder.Default
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "gift_certificate_m2m_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tagEntities;
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @Builder.Default
    @OneToMany(mappedBy = "certificateEntity", fetch = FetchType.LAZY)
    private List<OrderCertificateEntity> orderCertificateEntityList;
}
