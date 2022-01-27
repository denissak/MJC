package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CertificateMapper {

    CertificateMapper INSTANCE = Mappers.getMapper(CertificateMapper.class);

    CertificateDto convertToCertificateDto (Certificate certificate);


    Certificate convertToCertificate (CertificateDto certificateDto);

}
