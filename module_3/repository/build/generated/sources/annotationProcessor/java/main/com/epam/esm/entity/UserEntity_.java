package com.epam.esm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_ {

	public static volatile SingularAttribute<UserEntity, Long> id;
	public static volatile SingularAttribute<UserEntity, String> login;

	public static final String ID = "id";
	public static final String LOGIN = "login";

}

