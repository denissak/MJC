package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tags")
@ToString(exclude = "tags")
public class CertificateDto extends RepresentationModel<CertificateDto> {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
    private LocalDateTime lastUpdateDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
    private LocalDateTime createDate;
    private String image;
    private List<TagDto> tags;
}
