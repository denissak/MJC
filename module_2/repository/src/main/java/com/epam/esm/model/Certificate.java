package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Certificate {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime lastUpdateDate;
    private LocalDateTime createDate;
    private List<Tag> tags;
}
