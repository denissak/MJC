package com.epam.esm.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"userDto", "certificateDto"})
public class OrderDto {
    private Long id;
    private Double cost;
    private LocalDateTime date;
    private UserDto userDto;
    private CertificateDto certificateDto;
}
