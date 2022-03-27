package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderDto.OrderDtoBuilder;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.OrderEntity.OrderEntityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T19:24:25+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    private final CertificateMapper certificateMapper;
    private final UserMapper userMapper;

    @Autowired
    public OrderMapperImpl(CertificateMapper certificateMapper, UserMapper userMapper) {

        this.certificateMapper = certificateMapper;
        this.userMapper = userMapper;
    }

    @Override
    public OrderDto convertToOrderDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.certificateDto( certificateEntityListToCertificateDtoList( orderEntity.getCertificateEntities() ) );
        orderDto.userDto( userMapper.convertToUserDto( orderEntity.getUserEntity() ) );
        orderDto.id( orderEntity.getId() );
        orderDto.name( orderEntity.getName() );
        orderDto.cost( orderEntity.getCost() );
        orderDto.date( orderEntity.getDate() );

        return orderDto.build();
    }

    @Override
    public OrderEntity convertToOrder(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        OrderEntityBuilder orderEntity = OrderEntity.builder();

        orderEntity.certificateEntities( certificateDtoListToCertificateEntityList( orderDto.getCertificateDto() ) );
        orderEntity.userEntity( userMapper.convertToUser( orderDto.getUserDto() ) );
        orderEntity.id( orderDto.getId() );
        orderEntity.name( orderDto.getName() );
        orderEntity.cost( orderDto.getCost() );
        orderEntity.date( orderDto.getDate() );

        return orderEntity.build();
    }

    protected List<CertificateDto> certificateEntityListToCertificateDtoList(List<CertificateEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<CertificateDto> list1 = new ArrayList<CertificateDto>( list.size() );
        for ( CertificateEntity certificateEntity : list ) {
            list1.add( certificateMapper.convertToCertificateDto( certificateEntity ) );
        }

        return list1;
    }

    protected List<CertificateEntity> certificateDtoListToCertificateEntityList(List<CertificateDto> list) {
        if ( list == null ) {
            return null;
        }

        List<CertificateEntity> list1 = new ArrayList<CertificateEntity>( list.size() );
        for ( CertificateDto certificateDto : list ) {
            list1.add( certificateMapper.convertToCertificate( certificateDto ) );
        }

        return list1;
    }
}
