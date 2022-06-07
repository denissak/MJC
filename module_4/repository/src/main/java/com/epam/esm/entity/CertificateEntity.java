package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tagEntities", "orderEntities"})
@ToString(exclude = {"tagEntities", "orderEntities"})
@Builder
@Entity
@Table(name = "gift_certificate")
public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime lastUpdateDate;
    private LocalDateTime createDate;
    private String image;
    @ManyToMany
    @JoinTable(
            name = "gift_certificate_m2m_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tagEntities;
    @ManyToMany(mappedBy = "certificateEntities")
    private List<OrderEntity> orderEntities;
}
