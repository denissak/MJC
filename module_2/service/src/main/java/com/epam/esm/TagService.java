package com.epam.esm;

import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;

import java.util.List;

public interface TagService {

    TagDto create (TagDto tagDto);

    TagDto readById (Long tagId);

    List<TagDto> readAll ();

    void delete (Long tagId);
}
