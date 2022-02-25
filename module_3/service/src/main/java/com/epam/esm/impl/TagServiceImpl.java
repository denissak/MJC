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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        TagEntity tag = tagRepository.readByName(tagDto.getName());
        if (tag != null) {
            throw DuplicateException.tagExists().get();
        }
        TagValidator.validate(tagDto);
        TagEntity tagEntity = tagRepository.create(tagMapper.convertToTag(tagDto));
        return tagMapper.convertToTagDto(tagEntity);
    }

    /**
     * Reads tag with passed id.
     *
     * @param tagId id of tag to be read
     * @return tag with passed id
     */
    @Override
    public TagDto readById(Long tagId) {
        TagEntity tag = tagRepository.readById(tagId);
        if (tag == null) {
            throw NotFoundException.notFoundWithTagId(tagId).get();
        }
        return tagMapper.convertToTagDto(tag);
    }

    /**
     * Reads all tagEntities.
     *
     * @return all tagEntities.
     */
    @Override
    public List<TagDto> readAll(int page, int size) {
        List<TagEntity> tagEntityList = tagRepository.readAll(page, size);
        List<TagDto> tagDtoList = new ArrayList<>(tagEntityList.size());
        for (TagEntity tagEntity : tagEntityList) {
            tagDtoList.add(tagMapper.convertToTagDto(tagEntity));
        }
        return tagDtoList;
    }

    @Override
    public TagDto getMostPopularTag() {
        return tagMapper.convertToTagDto(tagRepository.getMostPopularTag());
    }

    /**
     * Deletes tag with passed id.
     *
     * @param tagId the id of tag to be deleted
     */
    @Transactional
    @Override
    public void delete(Long tagId) {
        readById(tagId);
        tagRepository.delete(tagId);
    }

    @Override
    @Transactional
    public void createAutoTag() {
        for (int i = 0; i < 1000; i++) {
            TagEntity tagEntity = TagEntity.builder()
                    .name("Tag " + i)
                    .build();
            tagRepository.createAutoTag(tagEntity);
        }
    }
}
