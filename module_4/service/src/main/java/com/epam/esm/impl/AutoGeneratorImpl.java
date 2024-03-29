package com.epam.esm.impl;

import com.epam.esm.AutoGenerator;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.*;
import com.epam.esm.util.DateTimeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Methods for database auto-generation
 */
@Service
public class AutoGeneratorImpl implements AutoGenerator {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final DateTimeWrapper dateTimeWrapper;

    @Autowired
    public AutoGeneratorImpl(UserRepository userRepository, OrderRepository orderRepository,
                             CertificateRepository certificateRepository, TagRepository tagRepository, DateTimeWrapper dateTimeWrapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.dateTimeWrapper = dateTimeWrapper;
    }

    /**
     * Auto-create Certificates in DataBase
     */
    @Transactional
    @Override
    public void createAutoCertificate() {
        List<TagEntity> tagEntities = tagRepository.findAll();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            LocalDateTime now = dateTimeWrapper.wrapDateTime();
            List<TagEntity> tagEntitiesUse = new ArrayList<>();
            int sizeTag = random.nextInt(5);
            for (int j = 0; j <= sizeTag; j++) {
                tagEntitiesUse.add(tagEntities.get(random.nextInt(1000)));
            }
            CertificateEntity certificateEntity = CertificateEntity.builder()
                    .name("certificate" + i)
                    .description("certificate" + i)
                    .price((double) random.nextInt(500))
                    .duration(random.nextInt(90))
                    .createDate(now)
                    .lastUpdateDate(now)
                    .tagEntities(tagEntitiesUse)
                    .build();
            certificateRepository.save(certificateEntity);
        }
    }

    /**
     * Auto-create Orders in DataBase
     */
    @Transactional
    @Override
    public void createAutoOrder() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<CertificateEntity> certificateEntities = certificateRepository.findAll();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            LocalDateTime now = dateTimeWrapper.wrapDateTime();
            List<CertificateEntity> certificateEntityList = new ArrayList<>();
            int sizeTag = random.nextInt(5);
            for (int j = 0; j <= sizeTag; j++) {
                certificateEntityList.add(certificateEntities.get(random.nextInt(1000)));
            }
            OrderEntity orderEntity = OrderEntity.builder()
                    .name("order" + i)
                    .cost((double) random.nextInt(500))
                    .date(now)
                    .userEntity(userEntities.get(random.nextInt(1000)))
                    .certificateEntities(certificateEntityList)
                    .build();
            orderRepository.save(orderEntity);
        }
    }

    /**
     * Auto-create Tags in DataBase
     */
    @Transactional
    @Override
    public void createAutoTag() {
        for (int i = 0; i < 1000; i++) {
            TagEntity tagEntity = TagEntity.builder()
                    .name("Tag " + i)
                    .build();
            tagRepository.save(tagEntity);
        }
    }

    /**
     * Auto-create Users in DataBase
     */
    @Transactional
    @Override
    public void createAutoUser() {
        for (int i = 0; i < 1000; i++) {
            UserEntity userEntity = UserEntity.builder()
                    .login("User " + i)
                    .password("$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO")
                    .roleEntity(RoleEntity.builder()
                            .id(2L)
                            .build())
                    .build();
            userRepository.save(userEntity);
        }
    }
}
