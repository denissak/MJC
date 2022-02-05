package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    TagRepository tagRepository;
    CertificateRepository certificateRepository;

    TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper, CertificateRepository certificateRepository) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        Optional<Tag> tag = tagRepository.readByName(tagDto.getName());
        if (tag.isPresent()) {
            throw (DuplicateException) DuplicateException.tagExists();
        }
        TagValidator.validate(tagDto);
        Optional<Tag> tagExist = tagRepository.readById(tagDto.getId());
        return tagMapper.convertToTagDto(tagExist.orElseGet(() -> (tagRepository.create(tagMapper.convertToTag(tagDto)))));
    }

    @Override
    public TagDto readById(Long tagId) {
        Optional<Tag> tag = tagRepository.readById(tagId);
        return tagMapper.convertToTagDto(tag.orElseThrow(NotFoundException.notFoundWithTagId(tagId)));
    }

    @Override
    public List<TagDto> readAll() {
        List<Tag> tagList = tagRepository.readAll();
        List<TagDto> tagDtoList = new ArrayList<>(tagList.size());
        for (Tag tag : tagList) {
            tagDtoList.add(tagMapper.convertToTagDto(tag));
        }
        return tagDtoList;
    }

    @Override
    public void delete(Long tagId) {
        readById(tagId);
        tagRepository.delete(tagId);
    }
}
