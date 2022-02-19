package com.epam.esm.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
//@EqualsAndHashCode(of = "login")
//@ToString(exclude = "orderDtoList")
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

        private Long id;
        private String login;
//        private List<OrderDto> orderDtoList;
}
