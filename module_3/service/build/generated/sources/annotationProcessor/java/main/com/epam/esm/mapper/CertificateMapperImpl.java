package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificateDto.CertificateDtoBuilder;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.CertificateEntity.CertificateEntityBuilder;
import com.epam.esm.entity.TagEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-22T15:47:31+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 16.0.2 (Oracle Corporation)"
)
@Component
public class CertificateMapperImpl implements CertificateMapper {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public CertificateDto convertToCertificateDto(CertificateEntity certificateEntity) {
        if ( certificateEntity == null ) {
            return null;
        }

        CertificateDtoBuilder certificateDto = CertificateDto.builder();

        certificateDto.tags( tagEntityListToTagDtoList( certificateEntity.getTagEntities() ) );
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

        certificateEntity.tagEntities( tagDtoListToTagEntityList( certificateDto.getTags() ) );
        certificateEntity.id( certificateDto.getId() );
        certificateEntity.name( certificateDto.getName() );
        certificateEntity.description( certificateDto.getDescription() );
        certificateEntity.price( certificateDto.getPrice() );
        certificateEntity.duration( certificateDto.getDuration() );
        certificateEntity.lastUpdateDate( certificateDto.getLastUpdateDate() );
        certificateEntity.createDate( certificateDto.getCreateDate() );

        return certificateEntity.build();
    }

    protected List<TagDto> tagEntityListToTagDtoList(List<TagEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<TagDto> list1 = new ArrayList<TagDto>( list.size() );
        for ( TagEntity tagEntity : list ) {
            list1.add( tagMapper.convertToTagDto( tagEntity ) );
        }

        return list1;
    }

    protected List<TagEntity> tagDtoListToTagEntityList(List<TagDto> list) {
        if ( list == null ) {
            return null;
        }

        List<TagEntity> list1 = new ArrayList<TagEntity>( list.size() );
        for ( TagDto tagDto : list ) {
            list1.add( tagMapper.convertToTag( tagDto ) );
        }

        return list1;
    }
}
