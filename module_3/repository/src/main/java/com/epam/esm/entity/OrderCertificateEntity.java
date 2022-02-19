//package com.epam.esm.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
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
//
//    public void setOrder(OrderEntity orderEntity){
//        this.orderEntity = orderEntity;
//        this.orderEntity.getOrderCertificateEntityList().add(this);
//    }
//
//    public void setCertificate (CertificateEntity certificateEntity){
//        this.certificateEntity = certificateEntity;
//        this.certificateEntity.getOrderCertificateEntityList().add(this);
//    }
//}
