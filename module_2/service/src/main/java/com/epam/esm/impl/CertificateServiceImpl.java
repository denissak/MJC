package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.util.DateTimeWrapper;
import com.epam.esm.validation.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    CertificateRepository certificateRepository;
    TagRepository tagRepository;
    CertificateMapper certificateMapper;
    TagMapper tagMapper;
    DateTimeWrapper dateTimeWrapper;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository, CertificateMapper certificateMapper, TagMapper tagMapper, DateTimeWrapper dateTimeWrapper) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
        this.dateTimeWrapper = dateTimeWrapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public CertificateDto create(CertificateDto certificateDto) {
        Optional<Certificate> certificate = certificateRepository.readByName(certificateDto.getName());
        if (certificate.isPresent()) {
            throw DuplicateException.certificateExists().get();
        }
        CertificateValidator.validate(certificateDto);
        LocalDateTime now = dateTimeWrapper.wrapDateTime();
        certificateDto.setCreateDate(now);
        certificateDto.setLastUpdateDate(now);
        Certificate createdCertificate = certificateRepository.create(certificateMapper.convertToCertificate(certificateDto));
        List<Tag> tags = certificateDto.getTags().stream().map(tagDto -> tagMapper.convertToTag(tagDto)).collect(Collectors.toList());
        createdCertificate.setTags(tags);
        addTagsToBase(createdCertificate);
        return certificateMapper.convertToCertificateDto(createdCertificate);
    }

    @Override
    public CertificateDto readById(Long certificateId) {
        Optional<Certificate> certificate = certificateRepository.readById(certificateId);
        certificate.ifPresent(actualCertificate -> actualCertificate.setTags(certificateRepository.readCertificateTags(certificateId)));
        return certificateMapper.convertToCertificateDto(certificate.orElseThrow(NotFoundException.notFoundWithCertificateId(certificateId)));
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
    public List<CertificateDto> readCertificateWithDifferentParams(String tagValue, String name, String description, String sortBy, String sortOrder) {
        List<Certificate> certificates = certificateRepository.readCertificateWithDifferentParams(tagValue, name, description, sortBy, sortOrder);
        List<CertificateDto> certificateDtoList = new ArrayList<>(certificates.size());
        for (Certificate certificate : certificates) {
            certificate.setTags(certificateRepository.readCertificateTags(certificate.getId()));
            certificateDtoList.add(certificateMapper.convertToCertificateDto(certificate));
        }
        return certificateDtoList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public CertificateDto update(long id, CertificateDto certificateDto) {
        CertificateValidator.validate(certificateDto);
        CertificateDto currentCertificateDto = checkFields(certificateDto);
        LocalDateTime now = dateTimeWrapper.wrapDateTime();
        currentCertificateDto.setLastUpdateDate(now);
        List<Tag> requestTags = currentCertificateDto.getTags().stream().map(tagDto -> tagMapper.convertToTag(tagDto)).collect(Collectors.toList());
        Set<Tag> createdTagsSet = new HashSet<>(tagRepository.readAll());
        List<Tag> responseTags = saveNewTags(requestTags, createdTagsSet);
        Certificate certificate = certificateMapper.convertToCertificate(currentCertificateDto);
        certificateRepository.update(id, certificate);
        certificate.setTags(responseTags);
        addTagsToBase(certificate);
        return currentCertificateDto;
    }

    @Override
    public void delete(Long certificateId) {
        readById(certificateId);
        certificateRepository.delete(certificateId);
    }

    public void addTagsToBase(Certificate certificate) {
        List<Tag> tagList = certificate.getTags();
        if (tagList != null) {
            for (Tag tag : tagList) {
                Optional<Tag> existedTag = tagRepository.readByName(tag.getName());
                Long tagId = existedTag.map(Tag::getId).orElseGet(() -> tagRepository.create(tag).getId());
                certificateRepository.addTag(tagId, certificate.getId());
            }
        }
    }

    private List<Tag> saveNewTags(List<Tag> requestTags, Set<Tag> createdTags) {
        List<Tag> responseTags = new ArrayList<>();
        for (Tag requestTag : requestTags) {
            if (!createdTags.contains(requestTag)) {
                responseTags.add(tagRepository.create(requestTag));
            }
        }
        return responseTags;
    }

    private CertificateDto checkFields(CertificateDto certificateDto) {
        CertificateDto certificateInBase = certificateMapper.convertToCertificateDto(certificateRepository.readById(certificateDto.getId())
                .orElseThrow(NotFoundException.notFoundWithCertificateId(certificateDto.getId())));

        if (!Objects.equals(certificateDto.getName(), certificateInBase.getName()) && certificateDto.getName() != null) {
            certificateInBase.setName(certificateDto.getName());
        }
        if (!Objects.equals(certificateDto.getDescription(), certificateInBase.getDescription()) && certificateDto.getDescription() != null) {
            certificateInBase.setDescription(certificateDto.getDescription());
        }
        if (!Objects.equals(certificateDto.getDuration(), certificateInBase.getDuration()) && certificateDto.getDuration() != null) {
            certificateInBase.setDuration(certificateDto.getDuration());
        }
        if (!Objects.equals(certificateDto.getPrice(), certificateInBase.getPrice()) && certificateDto.getPrice() != null) {
            certificateInBase.setPrice(certificateDto.getPrice());
        }
        if (!Objects.equals(certificateDto.getTags(), certificateInBase.getTags()) && certificateDto.getTags() != null) {
            certificateInBase.setTags(certificateDto.getTags());
        }
        certificateInBase.setLastUpdateDate(certificateDto.getCreateDate());
        return certificateInBase;
    }
}
