package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto convertToTagDto(TagEntity tagEntity);


    TagEntity convertToTag(TagDto tagDto);
}
