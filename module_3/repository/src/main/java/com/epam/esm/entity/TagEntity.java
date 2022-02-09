package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "certificateEntity")
@Builder
@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @Builder.Default
    @ManyToMany(mappedBy = "tagEntities")
    private List<CertificateEntity> certificateEntity;
}
