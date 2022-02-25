package com.epam.esm.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CertificateEntity.class)
public abstract class CertificateEntity_ {

	public static volatile SingularAttribute<CertificateEntity, Integer> duration;
	public static volatile ListAttribute<CertificateEntity, OrderCertificateEntity> orderCertificateEntityList;
	public static volatile SingularAttribute<CertificateEntity, Double> price;
	public static volatile SingularAttribute<CertificateEntity, LocalDateTime> lastUpdateDate;
	public static volatile ListAttribute<CertificateEntity, TagEntity> tagEntities;
	public static volatile SingularAttribute<CertificateEntity, String> name;
	public static volatile SingularAttribute<CertificateEntity, String> description;
	public static volatile SingularAttribute<CertificateEntity, Long> id;
	public static volatile SingularAttribute<CertificateEntity, LocalDateTime> createDate;

	public static final String DURATION = "duration";
	public static final String ORDER_CERTIFICATE_ENTITY_LIST = "orderCertificateEntityList";
	public static final String PRICE = "price";
	public static final String LAST_UPDATE_DATE = "lastUpdateDate";
	public static final String TAG_ENTITIES = "tagEntities";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CREATE_DATE = "createDate";

}

