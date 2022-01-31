package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto convertToTagDto (Tag tag);

    Tag convertToTag (TagDto tagDto);

}
