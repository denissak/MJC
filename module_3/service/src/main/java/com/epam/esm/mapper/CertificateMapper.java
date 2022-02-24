package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.CertificateEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface CertificateMapper {
    CertificateMapper INSTANCE = Mappers.getMapper(CertificateMapper.class);

    @Mapping(source = "tagEntities", target = "tags")
    CertificateDto convertToCertificateDto(CertificateEntity certificateEntity);

    @InheritInverseConfiguration
    @Mapping(target = "tagEntities", ignore = true)
    CertificateEntity convertToCertificate(CertificateDto certificateDto);
}
