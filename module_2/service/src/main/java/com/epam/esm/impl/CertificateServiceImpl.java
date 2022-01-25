package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CertificateServiceImpl implements CertificateService {

    CertificateRepository certificateRepository;
    TagRepository tagRepository;
    CertificateMapper certificateMapper;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        return null;
    }

    @Override
    public CertificateDto readById(Long certificateId) {
        return null;
    }

    @Override
    public List<CertificateDto> readAll() {
        return null;
    }

    @Override
    public CertificateDto update(Long certificateId, CertificateDto certificateDto) {
        return null;
    }
}
