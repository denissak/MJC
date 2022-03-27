package com.epam.esm.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderEntity.class)
public abstract class OrderEntity_ {

	public static volatile SingularAttribute<OrderEntity, LocalDateTime> date;
	public static volatile SingularAttribute<OrderEntity, UserEntity> userEntity;
	public static volatile ListAttribute<OrderEntity, CertificateEntity> certificateEntities;
	public static volatile SingularAttribute<OrderEntity, Double> cost;
	public static volatile SingularAttribute<OrderEntity, String> name;
	public static volatile SingularAttribute<OrderEntity, Long> id;

	public static final String DATE = "date";
	public static final String USER_ENTITY = "userEntity";
	public static final String CERTIFICATE_ENTITIES = "certificateEntities";
	public static final String COST = "cost";
	public static final String NAME = "name";
	public static final String ID = "id";

}

