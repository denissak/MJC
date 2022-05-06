package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userDto", "certificateDtoId"})
public class OrderDtoId extends RepresentationModel<OrderDto> {
    private Long id;
    private String name;
    private Double cost;
    private LocalDateTime date;
    private UserDto userDto;
    private List<Long> certificateDtoId;
}
