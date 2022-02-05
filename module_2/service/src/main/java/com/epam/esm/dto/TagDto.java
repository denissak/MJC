package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)*/
public class TagDto {

    private Long id;
    private String name;
}
