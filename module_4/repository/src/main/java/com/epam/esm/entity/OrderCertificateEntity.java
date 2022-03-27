//package com.epam.esm.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(exclude = {"certificateEntity", "orderEntity"})
//@ToString(exclude = {"certificateEntity", "orderEntity"})
//@Builder
//@Entity
//@Table(name = "order_certificate_m2m")
//public class OrderCertificateEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private OrderEntity orderEntity;
//    @ManyToOne
//    @JoinColumn(name = "certificate_id")
//    private CertificateEntity certificateEntity;
//}
