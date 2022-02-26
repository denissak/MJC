package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDto.TagDtoBuilder;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.entity.TagEntity.TagEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-25T18:04:19+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.14 (Amazon.com Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto convertToTagDto(TagEntity tagEntity) {
        if ( tagEntity == null ) {
            return null;
        }

        TagDtoBuilder tagDto = TagDto.builder();

        tagDto.id( tagEntity.getId() );
        tagDto.name( tagEntity.getName() );

        return tagDto.build();
    }

    @Override
    public TagEntity convertToTag(TagDto tagDto) {
        if ( tagDto == null ) {
            return null;
        }

        TagEntityBuilder tagEntity = TagEntity.builder();

        tagEntity.id( tagDto.getId() );
        tagEntity.name( tagDto.getName() );

        return tagEntity.build();
    }
}
