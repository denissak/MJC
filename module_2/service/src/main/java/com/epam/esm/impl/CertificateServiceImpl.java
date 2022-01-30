package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
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
        certificateDto.setCreateDate(LocalDateTime.now());
        certificateDto.setLastUpdateDate(LocalDateTime.now());
        Certificate createdCertificate = certificateRepository.create(certificateMapper.convertToCertificate(certificateDto));
        createdCertificate.setTags(certificateDto.getTags());
        addTagsToBase(createdCertificate);
        return certificateMapper.convertToCertificateDto(createdCertificate);
    }

    @Override
    public CertificateDto readById(Long certificateId) {
        Optional<Certificate> certificate = certificateRepository.readById(certificateId);
        certificate.ifPresent(actualCertificate -> actualCertificate.setTags(certificateRepository.readCertificateTags(certificateId)));
        return certificateMapper.convertToCertificateDto(certificate.orElseThrow(null)); //TODO CUSTOM EX
    }

    @Override
    public List<CertificateDto> readAll() {
        List<Certificate> certificates = certificateRepository.readAll();
        List<CertificateDto> certificateDtoList = new ArrayList<>(certificates.size());
        for (Certificate certificate : certificates) {
            certificate.setTags(certificateRepository.readCertificateTags(certificate.getId()));
            certificateDtoList.add(certificateMapper.convertToCertificateDto(certificate));
        }
        return certificateDtoList;
    }

    @Override
    public List<CertificateDto> readCertificateWithDifferentParams(String tagValue, String query, String sort, boolean ascending) {
        List<Certificate> certificates = certificateRepository.readCertificateWithDifferentParams(tagValue, query, sort, ascending);
        List<CertificateDto> certificateDtoList = new ArrayList<>(certificates.size());
        for (Certificate certificate : certificates) {
            certificate.setTags(certificateRepository.readCertificateTags(certificate.getId()));
            certificateDtoList.add(certificateMapper.convertToCertificateDto(certificate));
        }
        return certificateDtoList;
    }

    @Override
    public CertificateDto update(Long certificateId, CertificateDto certificateDto) {
        certificateDto.setId(certificateId);
        certificateDto.setLastUpdateDate(LocalDateTime.now());
        Certificate certificate = certificateMapper.convertToCertificate(certificateDto);
        List<Tag> requestTags = certificate.getTags();
        List<Tag> createdTags = tagRepository.readAll();
        saveNewTags(requestTags, createdTags);
        certificateRepository.update(certificateMapper.convertToCertificate(certificateDto));
        return certificateDto;
    }

    @Override
    public void delete(Long certificateId) {
        certificateRepository.delete(certificateId);
    }

    private void addTagsToBase(Certificate certificate) {
        List<Tag> tagList = certificate.getTags();
        if (tagList != null) {
            for (Tag tag : tagList) {
                Optional<Tag> existedTag = tagRepository.readByName(tag.getName());
                Long tagId = existedTag.map(Tag::getId).orElseGet(() -> tagRepository.create(tag).getId());
                certificateRepository.addTag(tagId, certificate.getId());
            }
        }
    }

    private void saveNewTags(List<Tag> requestTags, List<Tag> createdTags) {
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
    }
}
