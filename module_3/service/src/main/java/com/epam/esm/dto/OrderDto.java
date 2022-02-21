package com.epam.esm.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"userDto", "certificateDto"})
public class OrderDto {
    private Long id;
    private Double cost;
    private LocalDateTime date;
    private UserDto userDto;
    private List<CertificateDto> certificateDto;
}
