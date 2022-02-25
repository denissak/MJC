package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDto.TagDtoBuilder;
import com.epam.esm.model.Tag;
import com.epam.esm.model.Tag.TagBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-10T11:01:52+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 16.0.2 (Oracle Corporation)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto convertToTagDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDtoBuilder tagDto = TagDto.builder();

        tagDto.id( tag.getId() );
        tagDto.name( tag.getName() );

        return tagDto.build();
    }

    @Override
    public Tag convertToTag(TagDto tagDto) {
        if ( tagDto == null ) {
            return null;
        }

        TagBuilder tag = Tag.builder();

        tag.id( tagDto.getId() );
        tag.name( tagDto.getName() );

        return tag.build();
    }
}
