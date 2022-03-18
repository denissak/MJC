package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.*;
import com.epam.esm.mapper.*;
import com.epam.esm.util.DateTimeWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final Long ORDER_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long CERTIFICATE_ID_1 = 1L;
    private static final Long TAG_ID_1 = 1L;
    private OrderEntity orderEntity;
    private OrderDto orderDto;
    private UserEntity userEntity;
    private UserDto userDto;
    private CertificateEntity certificateEntity;
    private CertificateDto certificateDto;
    private TagEntity tagEntity;
    private TagDto tagDto;
    private List<CertificateEntity> certificateEntityList = new ArrayList<>();
    private List<CertificateDto> certificateDtoList = new ArrayList<>();
    private List<OrderEntity> orderEntityList = new ArrayList<>();
    private List<OrderDto> orderDtoList = new ArrayList<>();
    private Pageable pageable;
    private Page<OrderEntity> orderEntityPage;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DateTimeWrapper dateTimeWrapper;
    private static OrderMapper orderMapper = new OrderMapperImpl(new CertificateMapperImpl(new TagMapperImpl()), new UserMapperImpl(new RoleMapperImpl()));
    private static UserMapper userMapper = new UserMapperImpl(new RoleMapperImpl());
    private static TagMapper tagMapper = new TagMapperImpl();
    private static RoleMapper roleMapper = new RoleMapperImpl();
    private static ReadOrderMapper readOrderMapper = new ReadOrderMapperImpl();
    private static CertificateMapper certificateMapper = new CertificateMapperImpl(tagMapper);
    private static LocalDateTime localDateTime;
    private OrderService orderServiceImpl;

    @BeforeAll
    public static void init() {
        orderMapper = new OrderMapperImpl(new CertificateMapperImpl(new TagMapperImpl()), new UserMapperImpl(new RoleMapperImpl()));
        userMapper = new UserMapperImpl(new RoleMapperImpl());
        certificateMapper = new CertificateMapperImpl(tagMapper);
        roleMapper = new RoleMapperImpl();
        localDateTime = LocalDateTime.now();
    }

    @BeforeEach
    public void setUp() {

        orderServiceImpl = new OrderServiceImpl(orderRepository, orderMapper, dateTimeWrapper, readOrderMapper, certificateMapper, userMapper);

        userEntity = UserEntity.builder()
                .id(USER_ID)
                .login("name1")
                .build();

        tagEntity = TagEntity.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        orderEntity = OrderEntity.builder()
                .id(ORDER_ID)
                .name("order1")
                .cost(5.0)
                .date(localDateTime)
                .userEntity(userEntity)
                .certificateEntities(certificateEntityList)
                .build();

        certificateEntity = CertificateEntity.builder()
                .id(CERTIFICATE_ID_1)
                .name("Some certificate")
                .description("test certificate")
                .price(8.0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tagEntities(List.of(tagEntity))
                .build();

        certificateEntityList.add(certificateEntity);

        tagDto = TagDto.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        certificateDto = CertificateDto.builder()
                .id(CERTIFICATE_ID_1)
                .name("Some certificate")
                .description("test certificate")
                .price(8.0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tags(List.of(tagDto))
                .build();

        certificateDtoList.add(certificateDto);

        userDto = UserDto.builder()
                .id(USER_ID)
                .login("name1")
                .build();

        orderDto = OrderDto.builder()
                .id(ORDER_ID)
                .name("order1")
                .cost(5.0)
                .date(localDateTime)
                .userDto(userDto)
                .certificateDto(certificateDtoList)
                .build();

        orderEntityList.add(orderEntity);
        orderDtoList.add(orderDto);
        orderEntityPage = new PageImpl<>(orderEntityList);
        pageable = PageRequest.of(0, 1000, Sort.by("id"));
    }

    @Test
    void create() {
        OrderDto expected = orderDto;
        when(orderRepository.save(any())).thenReturn(orderEntity);
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(orderRepository.findByName(anyString())).thenReturn(null);
        OrderDto actual = orderServiceImpl.create(expected);
        assertEquals(expected, actual);
        verify(orderRepository).save(any());
        verify(dateTimeWrapper).wrapDateTime();
        verify(orderRepository).findByName(any());
    }

    @Test
    void readById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(orderEntity));
        orderServiceImpl.readById(certificateEntity.getId());
    }

    @Test
    void readAll() {
        List<OrderDto> expected = orderDtoList;
        when(orderRepository.findAll(pageable)).thenReturn(orderEntityPage);
        List<OrderDto> actual = orderServiceImpl.readAll(0, 1000);
        assertEquals(expected, actual);
        verify(orderRepository).findAll(pageable);
    }

    @Test
    void delete() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.ofNullable(orderEntity));
        orderServiceImpl.delete(ORDER_ID);
        verify(orderRepository).delete(orderEntity);
    }

    @Test
    void readAllOrdersByUserId() {
        List<OrderDto> expected = orderDtoList;
        when(orderRepository.findAllById(USER_ID, pageable)).thenReturn(orderEntityList);
        List<OrderDto> actual = orderServiceImpl.readAllOrdersByUserId(USER_ID,0, 1000);
        assertEquals(expected, actual);
        verify(orderRepository).findAllById(USER_ID, pageable);
    }

    @Test
    void readCostAndDateOrderByUserId() {
        List<OrderDto> expected = orderDtoList;
        when(orderRepository.findAllById(USER_ID, pageable)).thenReturn(orderEntityList);
        List<OrderDto> actual = orderServiceImpl.readAllOrdersByUserId(USER_ID,0, 1000);
        assertEquals(expected, actual);
        verify(orderRepository).findAllById(USER_ID, pageable);
    }
}
