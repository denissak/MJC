package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadOrderDto extends RepresentationModel<ReadOrderDto> {

    private Long id;
    private String name;
    private Double cost;
    private LocalDateTime date;
}
