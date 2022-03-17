package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.CertificateEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = TagMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CertificateMapper {
    CertificateMapper INSTANCE = Mappers.getMapper(CertificateMapper.class);

    @Mapping(source = "tagEntities", target = "tags")
    CertificateDto convertToCertificateDto(CertificateEntity certificateEntity);

//    @InheritInverseConfiguration
//    @Mapping(target = "tagEntities", ignore = true)
    CertificateEntity convertToCertificate(CertificateDto certificateDto);
}
