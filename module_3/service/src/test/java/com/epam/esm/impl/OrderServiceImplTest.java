package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.impl.OrderRepositoryImpl;
import com.epam.esm.dao.impl.PaginationHandlerImpl;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.*;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.*;
import com.epam.esm.util.DateTimeWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private static final Long INVALID_ID = -1L;
    private OrderEntity orderEntity;
    private OrderDto orderDto;
    private UserEntity userEntity;
    private UserDto userDto;
    private CertificateEntity certificateEntity;
    private CertificateDto certificateDto;
    private TagEntity tagEntity;
    private TagDto tagDto;
    private List<OrderCertificateEntity> orderCertificateEntityList = new ArrayList<>();
    private List<CertificateDto> certificateDtoList = new ArrayList<>();
    private OrderCertificateEntity orderCertificateEntity;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DateTimeWrapper dateTimeWrapper;
    private static OrderMapper orderMapper = new OrderMapper(new CertificateMapperImpl(new TagMapperImpl()));
    private static UserMapper userMapper = new UserMapperImpl();
    private static TagMapper tagMapper = new TagMapperImpl();
    private static ReadOrderMapper readOrderMapper = new ReadOrderMapperImpl();
    private static CertificateMapper certificateMapper = new CertificateMapperImpl(tagMapper);
    private static LocalDateTime localDateTime;
    private OrderService orderServiceImpl;

    @BeforeAll
    public static void init() {
        orderMapper = new OrderMapper(certificateMapper);
        userMapper = new UserMapperImpl();
        certificateMapper = new CertificateMapperImpl(tagMapper);
        localDateTime = LocalDateTime.now();
    }

    @BeforeEach
    public void setUp() {

        orderServiceImpl = new OrderServiceImpl(orderRepository, orderMapper, dateTimeWrapper, readOrderMapper);

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
                .orderCertificateEntityList(orderCertificateEntityList)
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

        orderCertificateEntity = OrderCertificateEntity.builder()
                .certificateEntity(certificateEntity)
                .orderEntity(orderEntity)
                .build();

        orderCertificateEntityList.add(orderCertificateEntity);

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
    }

    @Test
    void create() {
        OrderDto expected = orderDto;
        when(orderRepository.create(any())).thenReturn(orderEntity);
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(orderRepository.readByName(anyString())).thenReturn(null);
        OrderDto actual = orderServiceImpl.create(expected);
        assertEquals(expected, actual);
        verify(orderRepository).create(any());
        verify(dateTimeWrapper).wrapDateTime();
        verify(orderRepository).readByName(any());
    }

    @Test
    void readById() {
        when(orderRepository.readById(anyLong())).thenReturn(orderEntity);
        orderServiceImpl.readById(certificateEntity.getId());
    }

    @Test
    void readAll() {
        List<OrderEntity> orderEntityList = List.of(orderEntity);
        List<OrderDto> expected = List.of(orderDto);
        when(orderRepository.readAll(anyInt(), anyInt())).thenReturn(orderEntityList);
        List<OrderDto> actual = orderServiceImpl.readAll(anyInt(), anyInt());
        assertEquals(expected, actual);
        verify(orderRepository).readAll(anyInt(), anyInt());
    }

    @Test
    void delete() {
        when(orderRepository.readById(ORDER_ID)).thenReturn(orderEntity);
        orderServiceImpl.delete(ORDER_ID);
        verify(orderRepository).delete(ORDER_ID);
    }

    @Test
    void readAllOrdersByUserId() {
        List<OrderEntity> orderEntityList = List.of(orderEntity);
        List<OrderDto> expected = List.of(orderDto);
        when(orderRepository.readAllOrdersByUserId(anyLong(), anyInt(), anyInt())).thenReturn(orderEntityList);
        List<OrderDto> actual = orderServiceImpl.readAllOrdersByUserId(anyLong(), anyInt(), anyInt());
        assertEquals(expected, actual);
        verify(orderRepository).readAllOrdersByUserId(anyLong(), anyInt(), anyInt());
    }

    @Test
    void readCostAndDateOrderByUserId() {
        List<OrderEntity> orderEntityList = List.of(orderEntity);
        List<OrderDto> expected = List.of(orderDto);
        when(orderRepository.readAllOrdersByUserId(anyLong(), anyInt(), anyInt())).thenReturn(orderEntityList);
        List<OrderDto> actual = orderServiceImpl.readAllOrdersByUserId(anyLong(), anyInt(), anyInt());
        assertEquals(expected, actual);
        verify(orderRepository).readAllOrdersByUserId(anyLong(), anyInt(), anyInt());
    }

    @Test
    void testReadByIdWithInvalidId() {
        assertThrows(NotFoundException.class, () -> orderServiceImpl.readById(INVALID_ID));
    }
}