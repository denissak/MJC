package com.epam.esm.mapper;

import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.dto.ReadOrderDto.ReadOrderDtoBuilder;
import com.epam.esm.entity.OrderEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-18T16:29:56+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 16.0.2 (Oracle Corporation)"
)
@Component
public class ReadOrderMapperImpl implements ReadOrderMapper {

    @Override
    public ReadOrderDto convertToReadOrderDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        ReadOrderDtoBuilder readOrderDto = ReadOrderDto.builder();

        readOrderDto.id( orderEntity.getId() );
        readOrderDto.name( orderEntity.getName() );
        readOrderDto.cost( orderEntity.getCost() );
        readOrderDto.date( orderEntity.getDate() );

        return readOrderDto.build();
    }
}
