package com.epam.esm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_ {

	public static volatile SingularAttribute<UserEntity, String> password;
	public static volatile SingularAttribute<UserEntity, Long> id;
	public static volatile SingularAttribute<UserEntity, String> login;
	public static volatile SingularAttribute<UserEntity, RoleEntity> roleEntity;

	public static final String PASSWORD = "password";
	public static final String ID = "id";
	public static final String LOGIN = "login";
	public static final String ROLE_ENTITY = "roleEntity";

}

