package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagServiceImpl implements TagService {

    TagRepository tagRepository;
    TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        Tag createTag = tagRepository.create(tagMapper.convertToTag(tagDto));
        return tagMapper.convertToTagDto(createTag); //TODO convert twice ???
    }

    @Override
    public TagDto readById(Long tagId) {
        Optional<Tag> tag = tagRepository.readById(tagId);
        if (tag.isEmpty()){
            throw new RuntimeException(); //TODO custom ex
        }
        return tagMapper.convertToTagDto(tag.get());
    }

    @Override
    public List<TagDto> readAll() {
        List<Tag> tagList = tagRepository.readAll();
        List<TagDto> tagDtoList = new ArrayList<>(tagList.size());
        for (Tag tag : tagList){
            tagDtoList.add(tagMapper.convertToTagDto(tag));
        }
        return tagDtoList;
    }

    @Override
    public int delete(Long tagId) {
        return 0;
    }
}
