package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
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

    /**
     * Creates and saves the passed certificate.
     *
     * @param certificateDto the certificate to be saved
     * @return saved certificate
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public CertificateDto create(CertificateDto certificateDto) {
        CertificateEntity certificate = certificateRepository.readByName(certificateDto.getName());
        if (certificate != null) {
            throw DuplicateException.certificateExists().get();
        }
        CertificateValidator.validate(certificateDto);
        LocalDateTime now = dateTimeWrapper.wrapDateTime();
        certificateDto.setCreateDate(now);
        certificateDto.setLastUpdateDate(now);
        CertificateEntity createdCertificateEntity = certificateMapper.convertToCertificate(certificateDto);
        List<TagEntity> tagEntities = certificateDto.getTags().stream().map(tagDto -> tagMapper.convertToTag(tagDto)).collect(Collectors.toList());
        createdCertificateEntity.setTagEntities(tagEntities);
        addTagsToDataBase(createdCertificateEntity);
        createdCertificateEntity = certificateRepository.create(createdCertificateEntity);
        return certificateMapper.convertToCertificateDto(createdCertificateEntity);
    }

    /**
     * Reads certificate with passed id.
     *
     * @param certificateId the id of certificate to be read
     * @return certificate with passed id
     */
    @Override
    public CertificateDto readById(Long certificateId) {
        CertificateEntity certificate = certificateRepository.readById(certificateId);
        if (certificate == null) {
            throw NotFoundException.notFoundWithCertificateId(certificateId).get();
        }
        return certificateMapper.convertToCertificateDto(certificate);
    }

    /**
     * Reads all certificates.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all certificates
     */
    @Override
    public List<CertificateDto> readAll(int page, int size) {
        return certificateRepository.readAll(page, size).stream().map(certificateMapper::convertToCertificateDto).collect(Collectors.toList());
    }

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     * @param page        numbers of page
     * @param size        number of elements per page
     *
     * @return all certificates from search terms
     */
    @Override
    public List<CertificateDto> readCertificateWithDifferentParams(String[] tagValue,
                                                                   String name,
                                                                   String description,
                                                                   String sortBy,
                                                                   String sortOrder,
                                                                   int page,
                                                                   int size) {
        return certificateRepository.readCertificateWithDifferentParams(tagValue, name, description, sortBy, sortOrder, page, size)
                .stream().map(certificateMapper::convertToCertificateDto).collect(Collectors.toList());
    }

    /**
     * Updates certificate fields with passed id
     *
     * @param id             certificate id which needs to be updated
     * @param certificateDto certificate entity which contains fields with new
     *                       values to be set
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public CertificateDto update(long id, CertificateDto certificateDto) {
        CertificateValidator.validate(certificateDto);
        CertificateDto currentCertificateDto = checkFields(certificateDto);
        LocalDateTime now = dateTimeWrapper.wrapDateTime();
        currentCertificateDto.setLastUpdateDate(now);
        List<TagEntity> requestTagEntities = currentCertificateDto.getTags().stream().map(tagDto -> tagMapper.convertToTag(tagDto)).collect(Collectors.toList());
        CertificateEntity certificateEntity = certificateMapper.convertToCertificate(currentCertificateDto);
        certificateEntity.setTagEntities(requestTagEntities);
        addTagsToDataBase(certificateEntity);
        certificateRepository.update(id, certificateEntity);
        return currentCertificateDto;
    }

    /**
     * Deletes certificate with passed id.
     *
     * @param certificateId the id of certificate to be deleted
     */

    @Transactional
    @Override
    public void delete(Long certificateId) {
        readById(certificateId);
        certificateRepository.delete(certificateId);
    }

    /**
     * Check tagEntities are existing. Add new tagEntities in database and attach them
     * on certificate.
     *
     * @param certificateEntity certificate which need read all tagEntities,
     *                          create all new tagEntities in database and attach
     *                          them on certificate
     */
    public void addTagsToDataBase(CertificateEntity certificateEntity) {
        List<TagEntity> tagEntityList = certificateEntity.getTagEntities();
        if (tagEntityList != null) {
            for (TagEntity tagEntity : tagEntityList) {
                TagEntity existedTag = tagRepository.readByName(tagEntity.getName());
                if (existedTag == null) {
                    tagRepository.create(tagEntity);
                }
            }
        }
    }

    /**
     * Check modified fields before update certificate
     *
     * @param certificateDto certificate which need to check
     */
    private CertificateDto checkFields(CertificateDto certificateDto) {
        CertificateDto certificateInBase = certificateMapper.convertToCertificateDto(certificateRepository.readById(certificateDto.getId()));
        if (certificateInBase == null) {
            throw NotFoundException.notFoundWithCertificateId(certificateDto.getId()).get();
        }
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
