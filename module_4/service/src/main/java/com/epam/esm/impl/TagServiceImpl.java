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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        TagEntity tagExist = tagRepository.readByName(tagDto.getName());
        if (tagExist != null) {
            throw DuplicateException.tagExists().get();
        }
        TagValidator.validate(tagDto);
        TagEntity tagEntity = tagRepository.save(tagMapper.convertToTag(tagDto));
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
        TagEntity tag = tagRepository.findById(tagId).get();
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return tagRepository.findAll(pageable).stream().map(tagMapper::convertToTagDto).collect(Collectors.toList());
    }

//    @Override
//    public TagDto getMostPopularTag() {
//        return tagMapper.convertToTagDto(tagRepository.getMostPopularTag());
//    }

    /**
     * Deletes tag with passed id.
     *
     * @param tagId the id of tag to be deleted
     */
    @Transactional
    @Override
    public void delete(Long tagId) {
        TagEntity tagEntity = tagRepository.findById(tagId).get();
        tagRepository.delete(tagEntity);
    }
}
