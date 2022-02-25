package com.epam.esm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderCertificateEntity.class)
public abstract class OrderCertificateEntity_ {

	public static volatile SingularAttribute<OrderCertificateEntity, CertificateEntity> certificateEntity;
	public static volatile SingularAttribute<OrderCertificateEntity, OrderEntity> orderEntity;
	public static volatile SingularAttribute<OrderCertificateEntity, Long> id;

	public static final String CERTIFICATE_ENTITY = "certificateEntity";
	public static final String ORDER_ENTITY = "orderEntity";
	public static final String ID = "id";

}

