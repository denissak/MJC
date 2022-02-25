package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificateDto.CertificateDtoBuilder;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDto.TagDtoBuilder;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Certificate.CertificateBuilder;
import com.epam.esm.model.Tag;
import com.epam.esm.model.Tag.TagBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-10T11:01:52+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 16.0.2 (Oracle Corporation)"
)
@Component
public class CertificateMapperImpl implements CertificateMapper {

    @Override
    public CertificateDto convertToCertificateDto(Certificate certificate) {
        if ( certificate == null ) {
            return null;
        }

        CertificateDtoBuilder certificateDto = CertificateDto.builder();

        certificateDto.id( certificate.getId() );
        certificateDto.name( certificate.getName() );
        certificateDto.description( certificate.getDescription() );
        certificateDto.price( certificate.getPrice() );
        certificateDto.duration( certificate.getDuration() );
        certificateDto.lastUpdateDate( certificate.getLastUpdateDate() );
        certificateDto.createDate( certificate.getCreateDate() );
        certificateDto.tags( tagListToTagDtoList( certificate.getTags() ) );

        return certificateDto.build();
    }

    @Override
    public Certificate convertToCertificate(CertificateDto certificateDto) {
        if ( certificateDto == null ) {
            return null;
        }

        CertificateBuilder certificate = Certificate.builder();

        certificate.id( certificateDto.getId() );
        certificate.name( certificateDto.getName() );
        certificate.description( certificateDto.getDescription() );
        certificate.price( certificateDto.getPrice() );
        certificate.duration( certificateDto.getDuration() );
        certificate.lastUpdateDate( certificateDto.getLastUpdateDate() );
        certificate.createDate( certificateDto.getCreateDate() );
        certificate.tags( tagDtoListToTagList( certificateDto.getTags() ) );

        return certificate.build();
    }

    protected TagDto tagToTagDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDtoBuilder tagDto = TagDto.builder();

        tagDto.id( tag.getId() );
        tagDto.name( tag.getName() );

        return tagDto.build();
    }

    protected List<TagDto> tagListToTagDtoList(List<Tag> list) {
        if ( list == null ) {
            return null;
        }

        List<TagDto> list1 = new ArrayList<TagDto>( list.size() );
        for ( Tag tag : list ) {
            list1.add( tagToTagDto( tag ) );
        }

        return list1;
    }

    protected Tag tagDtoToTag(TagDto tagDto) {
        if ( tagDto == null ) {
            return null;
        }

        TagBuilder tag = Tag.builder();

        tag.id( tagDto.getId() );
        tag.name( tagDto.getName() );

        return tag.build();
    }

    protected List<Tag> tagDtoListToTagList(List<TagDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Tag> list1 = new ArrayList<Tag>( list.size() );
        for ( TagDto tagDto : list ) {
            list1.add( tagDtoToTag( tagDto ) );
        }

        return list1;
    }
}
