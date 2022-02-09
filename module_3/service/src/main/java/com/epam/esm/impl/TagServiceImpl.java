package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contains methods implementation for working mostly with {@code TagDto}
 * entity.
 */
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

    /**
     * Creates and saves the passed tag.
     *
     * @param tagDto the tag to be saved
     * @return saved tag
     */
    @Override
    public TagDto create(TagDto tagDto) {
        Optional<TagEntity> tag = tagRepository.readByName(tagDto.getName());
        if (tag.isPresent()) {
            throw (DuplicateException) DuplicateException.tagExists();
        }
        TagValidator.validate(tagDto);
        Optional<TagEntity> tagExist = tagRepository.readById(tagDto.getId());
        tagRepository.create(tagMapper.convertToTag(tagDto));
        return tagMapper.convertToTagDto(tagExist.orElseGet(() -> (tagRepository.create(tagMapper.convertToTag(tagDto)))));
    }

    /**
     * Reads tag with passed id.
     *
     * @param tagId id of tag to be read
     * @return tag with passed id
     */
    @Override
    public TagDto readById(Long tagId) {
        Optional<TagEntity> tag = tagRepository.readById(tagId);
        return tagMapper.convertToTagDto(tag.orElseThrow(NotFoundException.notFoundWithTagId(tagId)));
    }

    /**
     * Reads all tagEntities.
     *
     * @return all tagEntities.
     */
    @Override
    public List<TagDto> readAll() {
        List<TagEntity> tagEntityList = tagRepository.readAll();
        List<TagDto> tagDtoList = new ArrayList<>(tagEntityList.size());
        for (TagEntity tagEntity : tagEntityList) {
            tagDtoList.add(tagMapper.convertToTagDto(tagEntity));
        }
        return tagDtoList;
    }

    /**
     * Deletes tag with passed id.
     *
     * @param tagId the id of tag to be deleted
     */
    @Override
    public void delete(Long tagId) {
        readById(tagId);
        tagRepository.delete(tagId);
    }
}
