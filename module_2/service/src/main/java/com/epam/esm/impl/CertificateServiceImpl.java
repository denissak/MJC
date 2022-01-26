package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

public class CertificateServiceImpl implements CertificateService {

    CertificateRepository certificateRepository;
    TagRepository tagRepository;
    CertificateMapper certificateMapper;
    TagMapper tagMapper;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository, CertificateMapper certificateMapper, TagMapper tagMapper) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        LocalDateTime now = LocalDateTime.now();
        certificateDto.setCreateDate(now);
        certificateDto.setLastUpdateDate(now);
        Certificate createdCertificate = certificateRepository.create(certificateMapper.convertToCertificate(certificateDto));


        return null;
    }

    @Override
    public CertificateDto readById(Long certificateId) {
        Optional<Certificate> certificate = certificateRepository.readById(certificateId);
        CertificateDto certificateDto = certificateMapper.convertToCertificateDto(certificate.get());
        certificateDto.setTags((List<TagDto>) readById(certificateId));
        return certificateDto; //TODO
    }

    @Override
    public List<CertificateDto> readAll() {
        return null;
    }

    @Override
    public CertificateDto update(Long certificateId, CertificateDto certificateDto) {
        return null;
    }

    private void addTagsToBase(CertificateDto certificateDto) {
        List<TagDto> tagDtos = certificateDto.getTags();
        if (tagDtos != null) {
            for (TagDto tagDto : tagDtos) {
                Optional<Tag> existedTag = tagRepository.readByName(tagDto.getName());
                Long tagId = existedTag.map(Tag::getId).orElseGet(() -> tagRepository.create(tagMapper.convertToTag(tagDto)).getId());
                certificateRepository.addTag(tagId, certificateDto.getId());
            }
        }
    }

/*    private void saveNewTags(List<Tag> requestTags, List<Tag> createdTags) {
        if (requestTags == null) {
            return;
        }
        for (Tag requestTag : requestTags) {
            boolean isExist = false;
            for (Tag createdTag : createdTags) {
                if (Objects.equals(requestTag.getName(), createdTag.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                tagRepository.create(requestTag);
            }
        }
    }*/
}
