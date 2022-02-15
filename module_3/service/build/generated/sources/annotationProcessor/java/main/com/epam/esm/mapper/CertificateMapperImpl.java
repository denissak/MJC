package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificateDto.CertificateDtoBuilder;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.CertificateEntity.CertificateEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-15T09:07:56+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 16.0.2 (Oracle Corporation)"
)
@Component
public class CertificateMapperImpl implements CertificateMapper {

    @Override
    public CertificateDto convertToCertificateDto(CertificateEntity certificateEntity) {
        if ( certificateEntity == null ) {
            return null;
        }

        CertificateDtoBuilder certificateDto = CertificateDto.builder();

        certificateDto.id( certificateEntity.getId() );
        certificateDto.name( certificateEntity.getName() );
        certificateDto.description( certificateEntity.getDescription() );
        certificateDto.price( certificateEntity.getPrice() );
        certificateDto.duration( certificateEntity.getDuration() );
        certificateDto.lastUpdateDate( certificateEntity.getLastUpdateDate() );
        certificateDto.createDate( certificateEntity.getCreateDate() );

        return certificateDto.build();
    }

    @Override
    public CertificateEntity convertToCertificate(CertificateDto certificateDto) {
        if ( certificateDto == null ) {
            return null;
        }

        CertificateEntityBuilder certificateEntity = CertificateEntity.builder();

        certificateEntity.id( certificateDto.getId() );
        certificateEntity.name( certificateDto.getName() );
        certificateEntity.description( certificateDto.getDescription() );
        certificateEntity.price( certificateDto.getPrice() );
        certificateEntity.duration( certificateDto.getDuration() );
        certificateEntity.lastUpdateDate( certificateDto.getLastUpdateDate() );
        certificateEntity.createDate( certificateDto.getCreateDate() );

        return certificateEntity.build();
    }
}
