package com.epam.esm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TagEntity.class)
public abstract class TagEntity_ {

	public static volatile ListAttribute<TagEntity, CertificateEntity> certificateEntity;
	public static volatile SingularAttribute<TagEntity, String> name;
	public static volatile SingularAttribute<TagEntity, Long> id;

	public static final String CERTIFICATE_ENTITY = "certificateEntity";
	public static final String NAME = "name";
	public static final String ID = "id";

}

