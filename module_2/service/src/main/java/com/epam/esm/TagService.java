package com.epam.esm;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {

    TagDto create (TagDto tagDto);

    TagDto readById (Long tagId);

    List<TagDto> readAll ();

    int delete (Long tagId);
}
