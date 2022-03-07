package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends RepresentationModel<RoleDto> implements GrantedAuthority {
    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
